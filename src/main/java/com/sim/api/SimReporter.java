package com.sim.api;

/**
 * This interface exists to provide reporting, metrics, logging, and accountability when an event is
 * executed. The simulated event should not know that is inside a simulation. However, the SimEvent
 * receives a visitor when it executes. It is the _visitor object_ that knows it is inside a
 * simulation. The visitor is responsible for logging, metrics, or even updating a visualization of
 * the simulation.
 */
public interface SimReporter {

  void report(SimEvent simEvent);
}
