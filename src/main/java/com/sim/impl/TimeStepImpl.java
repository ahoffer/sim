package com.sim.impl;

import com.sim.api.SimEvent;
import com.sim.api.SimTimeStep;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeStepImpl implements SimTimeStep {

  final List<SimEvent> runnableEvents = new ArrayList<>();
  final long time;

  public TimeStepImpl(long time) {
    this.time = time;
  }

  @Override
  public boolean add(List<SimEvent> simEvents) {
    return this.runnableEvents.addAll(simEvents);
  }

  @Override
  public int compareTo(SimTimeStep ts) {
    if (this.equals(ts)) {
      // There can only be one object for a given time value.
      throw new IllegalArgumentException(
          // If you got here, you violated the Highlander rule:
          // THERE CAN BE ONLY ONE!
          String.format("Two time steps have the same time value \"%s\"", getTime()));
    }
    return Long.compare(getTime(), ts.getTime());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TimeStepImpl timeStep)) return false;
    return time == timeStep.time;
  }

  @Override
  public List<SimEvent> getSimRunnableEvents() {
    return new ArrayList<>(runnableEvents);
  }

  @Override
  public long getTime() {
    return time;
  }

  @Override
  public int hashCode() {
    return Objects.hash(time);
  }

  @Override
  public void run() {
    // Execute in order.
    // Wait for one to finish before starting the next.
    for (SimEvent e : runnableEvents) {
      e.run();
    }
  }
}
