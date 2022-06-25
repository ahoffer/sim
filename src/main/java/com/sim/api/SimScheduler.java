package com.sim.api;

/**
 * The purpose of this class is to decide if it is time to create more events. Implementation could
 * make a decision based on a frequency settings, a probability distribution, or random noise.
 * Implementation are expected to manage their own state.
 */
public interface SimScheduler {

    /**
     * Return true if it is time to create a SimEvent.
     *
     * @param time
     * @return The number of events to generate for this time step
     */
    long quantity(long time);
}
