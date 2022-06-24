package com.sim.api;

import java.util.List;

public interface SimTimeStep extends Comparable<SimTimeStep> {

  boolean add(SimExecutable sx);

  void execute();

  List<SimExecutable> getSimExecutables();

  /**
   * The executable's scheduled time to run.
   *
   * @return simulation time
   */
  long getTime();

  boolean remove(SimExecutable sx);
}
