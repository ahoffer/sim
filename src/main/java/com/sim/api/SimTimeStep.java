package com.sim.api;

import java.util.List;

public interface SimTimeStep extends Comparable<SimTimeStep>, Runnable {

  boolean add(List<SimEvent> runnableEvents);

  List<SimEvent> getSimRunnableEvents();

  /**
   * The executable's scheduled time to run.
   *
   * @return simulation time
   */
  long getTime();
}
