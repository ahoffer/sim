package com.sim.impl;

import com.sim.api.SimExecutable;
import com.sim.api.SimTimeStep;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimEventImpl implements SimTimeStep {

  final List<SimExecutable> executables = new ArrayList<>();
  final long time;

  public SimEventImpl(long time) {
    this.time = time;
  }

  @Override
  public boolean add(SimExecutable sx) {
    return executables.add(sx);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimEventImpl simEvent = (SimEventImpl) o;
    return time == simEvent.time;
  }

  @Override
  public int hashCode() {
    return Objects.hash(time);
  }

  @Override
  public int compareTo(SimTimeStep ts) {
    if (this.equals(ts)) {
      // There can only be one time step object for each moment of time.
      throw new IllegalArgumentException(
          String.format("Two time steps have the same time value \"%s\"", getTime()));
    }
    return Long.compare(getTime(), ts.getTime());
  }

  @Override
  public void execute() {
    executables.forEach(SimExecutable::execute);
  }

  @Override
  public List<SimExecutable> getSimExecutables() {
    return new ArrayList<>(executables);
  }

  @Override
  public long getTime() {
    return 0;
  }

  @Override
  public boolean remove(SimExecutable sx) {
    return executables.remove(sx);
  }
}
