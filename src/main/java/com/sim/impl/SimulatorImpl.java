package com.sim.impl;

import com.sim.api.SimTimeStep;
import com.sim.api.SimWorker;
import com.sim.api.Simulator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatorImpl implements Simulator {

  final ExecutorService executorService;
  long currentTime = 0;
  final SimWorker simWorker;

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

  public void skipTo(long time) {
    currentTime = time;
  }

  long advanceOneStep() throws ExecutionException, InterruptedException {
    long newTime = currentTime++;
    Future<SimTimeStep> future = executorService.submit(() -> simWorker.executeTime(newTime));

    // Pause until the time step finishes its execution.
    // The executor scheduler in use should specify a timeout appropriate to what the user wants.
    // I want to keep ths simulator easy to reason about. That means one time step must finish
    // before the next one starts.
    future.get();

    return newTime;
  }

  // run() should only be called by start()
  void run() throws ExecutionException, InterruptedException, TimeoutException {

    if (!halt.get()) {
      advanceOneStep();
    }
  }

  @Override
  public void start() {
    halt.set(false);
  }

  @Override
  public void stop() {
    halt.set(true);
  }
}
