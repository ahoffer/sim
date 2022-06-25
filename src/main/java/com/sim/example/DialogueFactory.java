package com.sim.example;

import com.sim.api.SimEventStream;
import com.sim.api.SimScheduler;
import com.sim.impl.SimEventFactoryNoOp;
import java.util.ArrayList;
import java.util.List;

public class DialogueFactory extends SimEventFactoryNoOp {
  public DialogueFactory(SimEventStream eventStream, SimScheduler eventScheduler) {
    super(eventStream, eventScheduler);
  }

  @Override
  public List<Runnable> getRunnableEventsAtTime(long time)
      throws SimEventStream.EndOfStreamException {
    List<Runnable> list = new ArrayList<>();
    long quantity = eventScheduler.quantity(time);
    for (long i = 0; i < quantity; i++) {
      list.add(eventStream.next());
    }
    return list;
  }
}
