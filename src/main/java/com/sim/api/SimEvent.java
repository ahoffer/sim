package com.sim.api;

public interface SimEvent {

    /**
     * Do something. Especially something that is visible to the outside world.
     * For example, a subclass could contain put a message on a queue when executed.
     */
    void execute();

}
