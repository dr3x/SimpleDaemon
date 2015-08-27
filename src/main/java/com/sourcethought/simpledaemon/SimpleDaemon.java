/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourcethought.simpledaemon;

import java.util.Timer;
import org.apache.commons.daemon.*;

/**
 *
 * @author cloudera
 */
public class SimpleDaemon implements Daemon {

    private boolean running = false;
    private static Timer timer = null;

    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        System.out.println("initializing ...");
    }

    @Override
    public void start() throws Exception {
        System.out.println("starting ...");
        running = true;
        timer = new Timer();
        timer.schedule(new EchoTask(), 0, 1000);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping ...");
        if (timer != null) {
            timer.cancel();
        }
        running = false;
    }

    @Override
    public void destroy() {
        System.out.println("done.");
    }

    public boolean isRunning() {
        return running;
    }

}
