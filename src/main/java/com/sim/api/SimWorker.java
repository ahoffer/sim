package com.sim.api;

/**
 * The main coordinator of the work. It is responsible for creating SimTimeSteps and populating them
 * with SimEvents produced by a factory. The intention of the SimWorker is to provide pluggable
 * persistence and state. For a simulation. Honestly, it might be overkill. I think I imagined
 * something one for Hydra, one for ECP, one for Vantage, and so on. A simulator could hold a
 * collection of SimWorker. But SimWorker is a pretty week abstraction. It might just be better to
 * let Vantage, Hydra, etc. implement a Simulator instead of SimWorker. That makes better intuitive
 * sense to me. The only advantage to having a SimWorker is if you needed to create Simulators that
 * all shared the same "time", but not the same set of events.
 *
 * <p>If each were its own Simulator, each would have its own notion of the current time. Actually,
 * that might be useful -- being able to compare what each simulation was doing at the same time
 * step. Maybe this is not a useful abstraction after all? Maybe it just needs a better name. Maybe
 * "Simulator" should be renamed to "SystemSimulation" and SimWork should be named "Simulator". That
 * makes more sense to me.
 */
public interface SimWorker {

  SimTimeStep executeTime(long time) throws SimEventStream.EndOfStreamException;
}
