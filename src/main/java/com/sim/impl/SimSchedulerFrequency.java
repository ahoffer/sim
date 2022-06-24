package com.sim.impl;

import com.sim.api.SimScheduler;

public class SimSchedulerFrequency implements SimScheduler {

  final int frequency;

  long timeOfLastTrue;

  /**
   * @param frequency The number of intervals (time) between events.
   */
  public SimSchedulerFrequency(int frequency) {
    this.frequency = frequency;
  }

  @Override
  public int quantity(long time) {

    int quantity = Math.floorMod(time - timeOfLastTrue, frequency);
    if (quantity > 0) {
      timeOfLastTrue = time;
    }
    return quantity;
  }
}
