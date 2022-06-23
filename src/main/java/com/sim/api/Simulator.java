package com.sim.api;

/**
 * Implementation track the current time and advance the time.
 */
public interface Simulator {

    long currentTime();

    SimWorker getWorker();

    long start();

    long stop();

}
