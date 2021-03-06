package com.sim.impl;

import static com.sim.api.SimEventStream.EndOfStreamException;

import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;
import com.sim.api.Simulator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatorImpl implements Simulator {

  final ExecutorService executorService;
  final SimWorker simWorker;
  long currentTime = 0;
  // NOTE: Currently only one thread is writing to this var and another thread is reading it.
  // I think marking it volatile would be enough for now. However, I can see that you might want
  // to halt the simulator if an exception bubbles up into this class.
  AtomicBoolean halt = new AtomicBoolean();
  TimeUnit timeoutUnit = TimeUnit.SECONDS;

  public SimulatorImpl(SimWorker simWorker, ExecutorService executorService) {
    this.executorService = executorService;
    this.simWorker = simWorker;
    this.halt.set(true);
  }

  @Override
  public long advanceOneStep() throws ExecutionException, InterruptedException, TimeoutException {
    // Simulation time is ONE BASED. The first time step is 1, not 0.
    Future<SimTimeStep> future =
        executorService.submit(
            () -> {
              try {
                return simWorker.executeTime(++currentTime);
              } catch (EndOfStreamException e) {
                throw new SimulationException(e);
              }
            });

    // Pause until the time step finishes its execution.
    // I want to keep ths simulator easy to reason about, but I don't want it to hand indefinitely.
    // Rule 1. A running timestep must finish before another one starts.
    // Rule 2. If a timestep runs too long, its thread must be killed.
    // For now, we just pass exceptions up (down?) the stack.
    future.get(getTimeoutValue(), timeoutUnit);
    return currentTime;
  }

  @Override
  public long currentTime() {
    return currentTime;
  }

  // Java lambdas and exception do not get along.
  // Added this method to make demo and testing easier.
  public void doit(String action)
      throws ExecutionException, InterruptedException, TimeoutException {
    switch (action) {
      case "start" -> start();
      case "stop" -> stop();
      case "advance" -> advanceOneStep();
    }
  }

  protected int getTimeoutValue() {
    return Integer.valueOf(System.getProperty("timeout", "60"));
  }

  // TODO: Is "skipping" a useful operation?
  // Do we need an advanceTo method that advances the time step instead of skipping to it?
  // Do we need both? How about advanceSteps(int x) or stepBackwareds(int x)?

  public void skipTo(long time) {

    // Minus one because the method to advance time will increment the value before execution.
    currentTime = time - 1;
  }

  @Override
  public void start() throws ExecutionException, InterruptedException, TimeoutException {
    boolean valueChangedFromHaltToRun = halt.compareAndSet(true, false);
    // There is no delay. It just runs as fast as it can.
    // TODO: Add a configurable pause between timesteps.
    while (valueChangedFromHaltToRun) {
      advanceOneStep();
    }
  }

  @Override
  public void stop() {
    halt.compareAndSet(false, true);
  }
}
