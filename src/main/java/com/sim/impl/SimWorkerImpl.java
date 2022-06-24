package com.sim.impl;

import static com.sim.api.SimEventStream.*;

import com.sim.api.SimEventFactory;
import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.IndexTreeList;

// TODO: Do we need BOTH TimeSteps that hold a list of executables AND a SimWorker?
// What PROBLEM does that solve?

public class SimWorkerImpl implements SimWorker {

  final List<SimEventFactory> factories;

//  IndexTreeList<Object> timeline;

  List<TimeStepImpl> timeline = new ArrayList<>();
  long lastExecutedTime;
  SimTimeStep ts;
  DB db;

  public SimWorkerImpl(List<SimEventFactory> factories) {
    this.factories = factories;
  }

  public SimTimeStep executeTime(long time) throws EndOfStreamException {
    ts = timeline.get((int) time);
    if (ts == null ) {
      // Timestep does not exist; create it.
      ts = new TimeStepImpl(time);
      for (SimEventFactory f : factories) {
        ts.add(f.getRunnableEventsAt(time));
        // It doesn't support long. That is disappointing for a disk-backed data structure.
        timeline.add(time, ts);
      }}
  }
/*
    public SimTimeStep WIPexecuteTime(long time) throws EndOfStreamException {
    try {
      if (db == null) {
        db = DBMaker.fileDB("target/" + Instant.now()).make();
         timeline = db.indexTreeList("simdata.bin").createOrOpen();
        // This thing doesn't allocate any space until add() is called. :-(
        // That messes up my assumption of randomly accessible list. It doesn't grow to accommodate and index.
        // Maybe I need a hash map instead.
      }
      lastExecutedTime = time;
      try {
        ts = (SimTimeStep) timeline.get((int) time);
      }
      // I want to check if anything exists at time x. I hoped get() would return null if that was the case,
      // but it throws an exception. There is probably a better way to do this.
      catch ( java.lang.IndexOutOfBoundsException e) {
        // Timestep does not exist; create it.
        ts = new TimeStepImpl(time);
        for (SimEventFactory f : factories) {
          ts.add(f.getRunnableEventsAt(time));
          // It doesn't support long. That is disappointing for a disk-backed data structure.
          timeline.add((int) time, ts);
        }
      }

      ts.run();
      return ts;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (db != null) {
        // TODO: Figure out persistence and transactions as needed.
        // timeline.commit();
        db.close();
      }
    }
  }
  */

}
