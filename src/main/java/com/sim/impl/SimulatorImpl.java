package com.sim.impl;

import com.sim.api.SimWorker;
import com.sim.api.Simulator;

public class SimulatorImpl implements Simulator {
    @Override
    public long currentTime() {
        return 0;
    }

    @Override
    public SimWorker getWorker() {
        return null;
    }

    @Override
    public long start() {
        return 0;
    }

    @Override
    public long stop() {
        return 0;
    }
}
