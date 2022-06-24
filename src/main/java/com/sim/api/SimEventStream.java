package com.sim.api;

public interface SimEventStream {
  public static class EndOfStreamException extends Throwable {
    public EndOfStreamException(String msg) {
      super(msg);
    }
  }
  /**
   * Create an event. Implementation should return a concrete type.
   *
   * @return Implementation of SimEvent
   */
  Runnable next() throws EndOfStreamException;
}
