package com.sim.impl;

import com.sim.api.SimEvent;
import com.sim.api.SimEventFactory;
import com.sim.api.SimEventStream;
import com.sim.api.SimScheduler;
import java.util.ArrayList;
import java.util.List;

/** Simple implementation that collaborates with a SimEventStream and SimScheduler. */
public class SimpleEventFactory implements SimEventFactory {

  protected final SimScheduler eventScheduler;
  protected final SimEventStream eventStream;

  public SimpleEventFactory(SimEventStream eventStream, SimScheduler eventScheduler) {
    this.eventStream = eventStream;
    this.eventScheduler = eventScheduler;
  }

  @Override
  public List<SimEvent> getRunnableEventsAtTime(long time)
      throws SimEventStream.EndOfStreamException {
    List<SimEvent> list = new ArrayList<>();
    long quantity = eventScheduler.quantity(time);
    for (long i = 0; i < quantity; i++) {
      list.add(eventStream.next());
    }
    return list;
  }
}
