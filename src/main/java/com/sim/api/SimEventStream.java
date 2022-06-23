package com.sim.api;

public interface SimEventStream {

    /**
     * Create an event. Implementation should return a concrete type.
     *
     * @return Implementation of SimEvent
     */
    SimEvent next();
}
