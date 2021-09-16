/*
 * Copyright (c) 2021. Lukas Jonsson
 */

package de.ruben.xdevapi.performance.concurrent;

public class CatchingRunnable implements Runnable {
    private final Runnable delegate;

    public CatchingRunnable(Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            delegate.run();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
