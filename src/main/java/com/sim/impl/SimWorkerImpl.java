package com.sim.impl;

import static com.sim.api.SimEventStream.EndOfStreamException;

import com.sim.api.SimEventFactory;
import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;
import java.time.Instant;
import java.util.List;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

// TODO: Do we need BOTH TimeSteps that hold a list of executables AND a SimWorker?
// What PROBLEM does that solve?

public class SimWorkerImpl implements SimWorker, AutoCloseable {

  final List<SimEventFactory> factories;

  DB db;
  private HTreeMap<Long, SimTimeStep> timeline;

  public SimWorkerImpl(List<SimEventFactory> factories) {
    this.factories = factories;
  }

  void checkInit() {
    // The index data structure did not allocate any space until add() was called.
    // That messes up my assumption of randomly accessible list.
    // Using map instead.
    if (db == null) {
      db = DBMaker.fileDB("target/" + Instant.now()).make();
      timeline =
          (HTreeMap<Long, SimTimeStep>)
              db.hashMap("timeline").keySerializer(Serializer.LONG).create();
    }
  }

  @Override
  public void close() {}

  public SimTimeStep executeTime(long time) throws EndOfStreamException {
    SimTimeStep tstep;
    checkInit();
    tstep = timeline.get(time);
    if (tstep == null) {
      tstep = makeTimeStep(time);
    }
    tstep.run();
    return tstep;
  }

  SimTimeStep makeTimeStep(long time) throws EndOfStreamException {
    SimTimeStep ts = new TimeStepImpl(time);
    for (SimEventFactory f : factories) {
      ts.add(f.getRunnableEventsAtTime(time));
    }
    timeline.put(time, ts);
    return ts;
  }
}
