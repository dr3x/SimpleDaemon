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
public class Main {

    public static String version = "0.1";

    public static void main(String[] args) throws Exception {
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

            final SimpleDaemon daemon = new SimpleDaemon();
            daemon.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    if (daemon.isRunning()) {
                        try {
                            System.out.println("Shutting down daemon...");
                            daemon.stop();
                        } catch (Exception ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }));

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
