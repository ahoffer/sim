package com.sim.impl;

import com.sim.api.SimEvent;
import com.sim.api.SimReporter;

/**
 * This is a no-op implementation of a sim event. It is a no-op because it does not produce any
 * simulated behavior. However, it does the one thing that implementations MUST do. It calls the
 * visit() method on the visitor and passes itself as the argument.
 */
public class SimEventNoOp implements SimEvent {
  @Override
  public void executeWith(SimReporter visitor) {
    visitor.report(this);
  }
}
