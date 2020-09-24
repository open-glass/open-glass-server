package de.seine_eloquenz.open_glass;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Main {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Configuration configuration = loadConfig();
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

    private static Configuration loadConfig() {
        final File configFile = new File("config.properties");
        final Parameters params = new Parameters();
        final FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased().setFile(configFile));
        try {
            return builder.getConfiguration();
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            return null;
        }
    }
}
