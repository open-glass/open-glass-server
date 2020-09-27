package de.seine_eloquenz.open_glass.cli

import de.seine_eloquenz.open_glass.common.Config
import org.apache.commons.configuration2.Configuration
import java.net.ConnectException
import java.net.URL

/**
 * Script to start server
 * This script validates command params and checks if the config file is present
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("""
            Provide one of the following arguments:
            status: check status of the server
            start: start the server
            stop: stop the server
        """.trimIndent())
        return
    }
    val config: Configuration? = Config.loadConfig()
    if (config == null) {
        println("Your server config seems to be missing!")
        return
    } else if ("" == config.getString("host")
            || "" == config.getString("port")
            || "" == config.getString("apiKey")
    ) {
        println("Your server config is incomplete!")
        return
    }

    fun running(): Boolean {
        val url = "http://${config.getString("host")}:${config.getInt("port")}/status?API_KEY=${config.getString("apiKey")}"
        return try {
            val conn = URL(url).openConnection()
            conn.readTimeout = 100
            conn.connect()
            conn.getInputStream().bufferedReader().readText().contains("running")
        } catch (e: ConnectException) {
            false
        }
    }

    when (args[0]) {
        "start" -> {
            if (!running()) {
                Runtime.getRuntime().exec("java -jar ogs.jar")
                println("Server started!")
            } else {
                println("Server is already running!")
            }
        }
        "stop" -> {
            fun shutdown() {
                val url = "http://${config.getString("host")}:${config.getInt("port")}/shutdown?API_KEY=${config.getString("apiKey")}"
                URL(url).readText()
            }
            if (running()) {
                shutdown()
                println("Server stopped.")
            } else {
                println("Server was not running.")
            }
        }
        "status" -> {
            when (running()) {
                true -> println("Server is running!")
                false -> println("Server is not running!")
            }
        }
    }
}