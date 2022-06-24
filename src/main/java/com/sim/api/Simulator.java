package com.sim.api;

/** The main coordinator of the work. */
public interface Simulator {

  long currentTime();

  void start();

  void stop();
}
