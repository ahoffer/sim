package com.sim.api;

import java.util.List;

public interface SimTimeStep extends Comparable<SimTimeStep>, Runnable {

    boolean add(List<Runnable> runnableEvents);

    List<Runnable> getSimRunnableEvents();

    /**
     * The executable's scheduled time to run.
     *
     * @return simulation time
     */
    long getTime();
}
