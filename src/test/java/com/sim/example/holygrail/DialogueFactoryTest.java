package com.sim.example.holygrail;

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
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DialogueTest {

  static {
    System.setProperty("timeout", Long.toString(60 * 60 * 62));
  }

  @Test
  void exhaustEventStream()  {
    // This is what Java looks like without injection.
    List<SimEventFactory> factories = new ArrayList<>();
    factories.add(new DialogueFactory(new SpeakOneLine(), new SimSchedulerFrequency(1)));
    try (SimWorkerImpl simWorker = new SimWorkerImpl(factories)) {
      Simulator simulator =
              new SimulatorImpl(simWorker, Executors.newSingleThreadScheduledExecutor());
      simulator.start();
    } catch (ExecutionException e) {
      String message = e.getCause().getCause().getMessage();
      Assertions.assertThat(message).isEqualTo("!!! No More Python For You !!!");
      System.err.println(message);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (TimeoutException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  void advance() throws  Exception{
    List<SimEventFactory> factories = new ArrayList<>();
    factories.add(new DialogueFactory(new SpeakOneLine(), new SimSchedulerFrequency(1)));
    try (SimWorkerImpl simWorker = new SimWorkerImpl(factories)) {
      Simulator simulator =
              new SimulatorImpl(simWorker, Executors.newSingleThreadScheduledExecutor());
      simulator.advanceOneStep();
      simulator.advanceOneStep();
      simulator.advanceOneStep();
    }
  }
}
