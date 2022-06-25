package com.sim.impl;

import com.sim.api.SimEventFactory;
import com.sim.api.SimEventStream;
import com.sim.api.SimScheduler;
import java.util.Collections;
import java.util.List;

/**
 * Simple implementation that collaborates with a SimEventStream and SimScheduler. It is a no op
 * because the public method getRunnableEventsAtTime() returns an empty collection. TYhe intention
 * is for subclasses to override that method and return a collection of runnable events.
 */
public class SimEventFactoryNoOp implements SimEventFactory {

  protected final SimScheduler eventScheduler;
  protected final SimEventStream eventStream;

  public SimEventFactoryNoOp(SimEventStream eventStream, SimScheduler eventScheduler) {
    this.eventStream = eventStream;
    this.eventScheduler = eventScheduler;
  }

  @Override
  public List<Runnable> getRunnableEventsAtTime(long time)
      throws SimEventStream.EndOfStreamException {
    return Collections.emptyList();
  }
}
