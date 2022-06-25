package com.sim.example.mq;

import java.util.Random;

public class AcculatingRandomScheduler implements com.sim.api.SimScheduler {

  final double probability;
  long lastWeTimeMadeSomething = 0;
  // Choose a  constant seed to make the simulation more repeatable.
  Random rng = new Random(13);

  public AcculatingRandomScheduler(double probability) throws IllegalAccessException {
    if (probability < 0 || probability > 1) {
      throw new IllegalAccessException("Probability must be between 0 and 1, inclusive");
    }
    this.probability = probability;
  }

  public long quantity(long time) {

    long quantity = 0;
    long interval = time - lastWeTimeMadeSomething;

    // If the random value is less than the probability, then an event occurred.
    for (int i = 0; i < interval; i++) {
      if (rng.nextDouble() < probability) {
        quantity++;
      }
    }
    if (quantity > 0) {
      lastWeTimeMadeSomething = time;
    }
    return quantity;
  }
}
