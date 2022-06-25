package com.sim.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * The timekeeper. It is intended to be the timekeeper and to responsible for advancing the
 * simulation through time.
 */
public interface Simulator {
  /**
   * Advance the simulation one step
   *
   * @return Return the step number that was executed
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws TimeoutException
   */
  long advanceOneStep() throws ExecutionException, InterruptedException, TimeoutException;

  long currentTime();

  void start() throws ExecutionException, InterruptedException, TimeoutException;

  void stop();

  class SimulationException extends RuntimeException {
    public SimulationException(Throwable throwable) {
      super(throwable);
    }
  }
}
