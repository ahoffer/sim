package com.sim.impl;

import com.sim.api.SimEventStream;
import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;
import com.sim.api.Simulator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.sim.api.SimEventStream.*;

public class SimulatorImpl implements Simulator {

  final ExecutorService executorService;
  // New simulations always start at time 0. To advance the time, use the skip function.
  long currentTime = 0;
  final SimWorker simWorker;

  TimeUnit timeUnit = TimeUnit.SECONDS;

  // NOTE: Currently only one thread is writing to this var and another thread is reading it.
  // I think marking it volatile would be enough for now. However, I can see that you might want
  // to halt the simulator if an exception bubbles up into this class.
  AtomicBoolean halt = new AtomicBoolean();

  public SimulatorImpl(SimWorker simWorker, ExecutorService executorService) {
    this.executorService = executorService;
    this.simWorker = simWorker;
    this.halt.set(true);
  }

  @Override
  public long currentTime() {
    return currentTime;
  }

  protected int getTimeoutValue() {
return Integer.valueOf(System.getProperty("timeout", "60"));
  }

  public void skipTo(long time) {
    currentTime = time;
  }

  // TODO: Does skipping a useful operation?
  // Do we need an advanceTo method that advances the time step instead of skipping to it?
  // Do we need both? How about advanceSteps(int x) or stepBackwareds(int x)?

  long advanceOneStep() throws ExecutionException, InterruptedException, TimeoutException {
    // Simulation time is ONE BASED. The first time step is 1, not 0.
    Future<SimTimeStep> future =
        executorService.submit(
            () -> {
              try {
                return simWorker.executeTime(++currentTime);
              } catch (EndOfStreamException e) {
                throw new RuntimeException(e);
              }
            });

    // Pause until the time step finishes its execution.
    // I want to keep ths simulator easy to reason about, but I don't want it to hand indefinitely.
    // Rule 1. A running timestep must finish before another one starts.
    // Rule 2. If a timestep runs too long, its thread must be killed.
    // For now, we just pass exceptions up (down?) the stack.
    future.get(getTimeoutValue(), timeUnit);
    return currentTime;
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
