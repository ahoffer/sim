package com.sim.impl;

import com.sim.api.SimEvent;
import com.sim.api.SimReporter;

public class SimReporterNoOp implements SimReporter {
    @Override
    public void report(SimEvent simEvent) {
        // No-op
    }
}
