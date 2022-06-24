package com.sim.impl;

import com.sim.api.SimEventFactory;
import com.sim.api.SimEventStream;
import com.sim.api.SimScheduler;
import java.util.Collections;
import java.util.List;

public class SimEventFactoryImpl implements SimEventFactory {

  protected final SimEventStream eventStream;
  protected final SimScheduler eventScheduler;

  public SimEventFactoryImpl(SimEventStream eventStream, SimScheduler eventScheduler) {
    this.eventStream = eventStream;
    this.eventScheduler = eventScheduler;
  }

  @Override
  public List<Runnable> getRunnableEventsAt(long time) throws SimEventStream.EndOfStreamException {
    return Collections.emptyList();
  }
}
