package com.sim.impl;

import com.sim.api.SimScheduler;

/**
 * A default implementation of the interface. It decides how many events should be generated based
 * on a constant frequency.
 */
public class SimSchedulerFrequency implements SimScheduler {

  final double frequency;

  long lastWeTimeMadeSomething = 0;

  /**
   * @param frequency The number events per time unit. If frequency is less than one, multiple time
   *     steps pass between events. If frequency is greater than one, multiple events are generated
   *     every time step.
   */
  public SimSchedulerFrequency(double frequency) {
    this.frequency = frequency;
  }

  @Override
  public long quantity(long time) {

    // Time step zero is going to be pretty boring.
    long interval = time - lastWeTimeMadeSomething;
    double rationalNumber = interval * frequency;
    // TODO: Ask if we really want the floor function. It is only necessary if our semantics are "at
    // MOST x events/timeunit"
    long quantity = Math.round(Math.floor(rationalNumber));
    if (quantity > 0) {
      lastWeTimeMadeSomething = time;
    }
    return quantity;
  }
}
