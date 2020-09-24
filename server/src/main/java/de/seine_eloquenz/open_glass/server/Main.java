package de.seine_eloquenz.open_glass.server;

import de.seine_eloquenz.open_glass.common.Config;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.util.logging.Level;

public class Main {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Configuration configuration = Config.INSTANCE.loadConfig();
        if (configuration == null) {
            OpenGlassServer.LOG.severe("No configuration found!");
            System.exit(1);
        }
        String hostname = configuration.getString("host");
        int port = configuration.getInt("port");
        String apiKey = configuration.getString("apiKey");
        OpenGlassServer server = new OpenGlassServer(hostname, port, apiKey);
        try {
            OpenGlassServer.LOG.info("Starting Server");
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            OpenGlassServer.LOG.log(Level.SEVERE, "Error starting the Server, dumping stacktrace:", e.getCause());
            System.exit(1);
        }
        synchronized (lock) {
            while (!server.shallStop()) {
                try {
                    lock.wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        OpenGlassServer.LOG.info("Stopping.");
        server.stop();
        OpenGlassServer.LOG.info("Server stopped successfully");
    }
}
