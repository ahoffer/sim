package com.sim.api;

import java.util.List;

public interface SimEventFactory {

  /**
   * Pairs together a SimScheduler and a SimEventStream. Uses information from SimsScheduler to
   * decided how many events to create. Uses the SimEventStream to create the events. If no events
   * should be created, return an empty collection.
   *
   * <p>NOT GUARANTEED TO BE PURE FUNCTION. The collection of event could be very different if the
   * method is called twice with the same time step.
   *
   * @param time step
   * @return list to events to execute at the provided time step
   */
  List<Runnable> getRunnableEventsAtTime(long time) throws SimEventStream.EndOfStreamException;
}
