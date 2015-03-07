package com.rusefi.ui;

import com.rusefi.core.EngineTimeListener;
import com.rusefi.io.LinkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionWatchdog {
    /**
     * 10 seconds
     */
    private static final int RESTART_DELAY = 10000;

    private ConnectionWatchdog() {
    }

    public static void start() {
        final Timer reconnectTimer = new Timer(RESTART_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkManager.restart();
            }
        });
        reconnectTimer.restart();

        LinkManager.engineState.timeListeners.add(new EngineTimeListener() {
            @Override
            public void onTime(double time) {
                /**
                 * this timer will reconnect
                 */
                reconnectTimer.restart();
            }
        });
    }
}