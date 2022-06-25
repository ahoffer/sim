package com.sim.api;

public interface SimEventStream {
  /**
   * Create an event. Implementation should return a concrete type. Originally, this method return a
   * Runnable. However, a runnable does not take input, which made meant there was no hook to do
   * reporting, metrics, or logging. I considered having this method return a
   * Consumer<SimEventReporter>. That still might be the way to go. Having a special interface means
   * that a simulation is going to need more classes. It's verbose. On the other hand, defining a
   * function in-place provides less context. It can be a lot more opaque when inspecting something
   * in a debugger. "Hy, what does this lambda do? I have no idea?"
   *
   * @return Implementation of SimEvent
   */
  SimEvent next() throws EndOfStreamException;

  class EndOfStreamException extends Throwable {
    public EndOfStreamException(String msg) {
      super(msg);
    }
  }
}
