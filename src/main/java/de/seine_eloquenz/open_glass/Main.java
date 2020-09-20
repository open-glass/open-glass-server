package de.seine_eloquenz.open_glass;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

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
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //TODO this will have to do for now, improve in the long run
        Scanner scanner = new Scanner(System.in);
        OpenGlassServer.LOG.info("Server started. Enter anything to stop");
        scanner.next();
        server.stop();
        System.exit(0);
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
