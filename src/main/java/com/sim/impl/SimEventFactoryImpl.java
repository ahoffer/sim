package com.sim.impl;

import com.sim.api.SimEvent;
import com.sim.api.SimEventFactory;
import java.util.Collections;
import java.util.List;

public class SimEventFactoryImpl implements SimEventFactory {

  @Override
  public List<SimEvent> getEventsAt(long time) {
    return Collections.emptyList();
  }
}
