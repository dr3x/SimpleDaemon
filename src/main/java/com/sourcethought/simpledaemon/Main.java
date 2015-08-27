/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourcethought.simpledaemon;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.daemon.*;
import org.apache.commons.cli.*;

class EchoTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(new Date() + " running ...");
    }
}

/**
 *
 * @author cloudera
 */
public class Main implements Daemon {
  public static String version = "0.1";    
    
  private static Timer timer = null;

    public static void main(String[] args) {
        // setup command line options
        Options options = new Options();
        options.addOption("h", "help", false, "print this help screen");
        
        // read command line options
        CommandLineParser parser = new PosixParser();
        try {        
            CommandLine cmdline = parser.parse(options, args);
            
            if (cmdline.hasOption("help") || cmdline.hasOption("h")) {
                System.out.println("SimpleDaemon Version " + Main.version);

                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -cp lib/*:target/SimpleDaemon-1.0-SNAPSHOT.jar com.sourcethought.simpledaemon.Main", options);
                return;
            }            
            
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        timer = new Timer();
        timer.schedule(new EchoTask(), 0, 1000);
    }

    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        System.out.println("initializing ...");
    }

    @Override
    public void start() throws Exception {
        System.out.println("starting ...");
        main(null);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping ...");
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void destroy() {
        System.out.println("done.");
    }    
    
}
