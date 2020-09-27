package de.seine_eloquenz.open_glass.common

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.apache.commons.configuration2.ex.ConfigurationException
import java.io.File

/**
 * Object responsible for loading the config file from disk
 */
object Config {

    fun loadConfig(): Configuration? {
        val configFile = File("config.properties")
        val params = Parameters()
        val builder = FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration::class.java)
                .configure(params.fileBased().setFile(configFile))
        return try {
            builder.configuration
        } catch (e: ConfigurationException) {
            null
        }
    }
}