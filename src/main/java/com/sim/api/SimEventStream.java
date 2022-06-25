package com.sim.api;

public interface SimEventStream {
    /**
     * Create an event. Implementation should return a concrete type.
     *
     * @return Implementation of SimEvent
     */
    Runnable next() throws EndOfStreamException;

    class EndOfStreamException extends Throwable {
        public EndOfStreamException(String msg) {
            super(msg);
        }
    }
}
