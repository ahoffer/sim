package com.sim.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/** The main coordinator of the work. */
public interface Simulator {

  long currentTime();

  void start() throws ExecutionException, InterruptedException, TimeoutException;

  void stop();
}
