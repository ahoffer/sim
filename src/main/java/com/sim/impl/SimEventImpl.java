package com.sim.impl;

import com.sim.api.SimExecutable;
import com.sim.api.SimTimeStep;

import java.util.ArrayList;
import java.util.List;

public class SimEventImpl implements SimTimeStep {

    final List<SimExecutable> executables = new ArrayList<>();
    final long time;

    public SimEventImpl(long time) {
        this.time = time;
    }

    @Override
    public boolean add(SimExecutable sx) {
        return executables.add(sx);
    }

    @Override
    public void execute() {
        executables.forEach(SimExecutable::execute);
    }

    @Override
    public List<SimExecutable> getSimExecutables() {
        return new ArrayList<>(executables);
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public boolean remove(SimExecutable sx) {
        return executables.remove(sx);
    }
}
