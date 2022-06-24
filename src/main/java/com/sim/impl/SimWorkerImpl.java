package com.sim.impl;

import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;
import org.mapdb.DB;
import org.mapdb.DBMaker;

public class SimWorkerImpl implements SimWorker {

  final DB timeline;

  public SimWorkerImpl() {
    timeline = DBMaker.fileDB("sim.db").make();
    timeline.indexTreeList("simdata").createOrOpen();
  }

  @Override
  public SimTimeStep executeTime(long time) {
    SimTimeStep ts = timeline.get(String.valueOf(time));
    ts.execute();
    return ts;
  }
}
