package com.sim.example;

import com.sim.api.SimEventFactory;
import com.sim.api.Simulator;
import com.sim.impl.SimSchedulerFrequency;
import com.sim.impl.SimWorkerImpl;
import com.sim.impl.SimulatorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;

class DialogueTest {


  static {
    System.setProperty("timeout", Long.toString(60*60*62));
  }

  @Test
  void unnamedtest() throws ExecutionException, InterruptedException, TimeoutException {
    // This is what Java looks like without injection.
    List<SimEventFactory> factories = new ArrayList<>();
    factories.add(new DialogueFactory(new SpeakOneLine(), new SimSchedulerFrequency(1)));
    Simulator simulator =
        new SimulatorImpl(
            new SimWorkerImpl(factories), Executors.newSingleThreadScheduledExecutor());
    simulator.start();
  }
}
