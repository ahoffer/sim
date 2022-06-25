package com.sim.example;

import com.sim.api.SimEventFactory;
import com.sim.api.Simulator;
import com.sim.impl.SimSchedulerFrequency;
import com.sim.impl.SimWorkerImpl;
import com.sim.impl.SimulatorImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

class DialogueTest {

    static {
        System.setProperty("timeout", Long.toString(60 * 60 * 62));
    }

    @Test
    void unnamedtest() throws Exception {
        // This is what Java looks like without injection.
        List<SimEventFactory> factories = new ArrayList<>();
        factories.add(new DialogueFactory(new SpeakOneLine(), new SimSchedulerFrequency(1)));
        try (SimWorkerImpl simWorker = new SimWorkerImpl(factories)) {
            Simulator simulator =
                    new SimulatorImpl(simWorker, Executors.newSingleThreadScheduledExecutor());
            simulator.start();
        } catch (ExecutionException e) {
            System.err.println(e.getCause().getCause().getMessage());
        }
    }
}
