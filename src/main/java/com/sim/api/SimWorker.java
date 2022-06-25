package com.sim.api;

/**
 * The main coordinator of the work. It is responsible for creating SimTimeSteps and populating them
 * with SimEvents. It is also responsible for calling execute on the events.
 */
public interface SimWorker {

  SimTimeStep executeTime(long time) throws SimEventStream.EndOfStreamException;
}
