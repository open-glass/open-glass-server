package de.seine_eloquenz.open_glass.cli

import de.seine_eloquenz.open_glass.common.Config
import org.apache.commons.configuration2.Configuration
import java.net.URL

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("""
            Provide at least one of the following arguments:
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
    }

    fun running(): Boolean {
        val url = "http://${config.getString("host")}:${config.getInt("port")}/status?API_KEY=${config.getString("apiKey")}"
        val res = URL(url).readText()
        return res.contains("running")
    }

    when (args[0]) {
        "start" -> {
            if (!running()) {
                Runtime.getRuntime().exec("java -jar ogs.jar")
            } else {
                println("Server is already running!")
            }
        }
        "stop" -> {
            fun shutdown() {
                val url = "http://${config.getString("host")}:${config.getInt("port")}/shutdown?API_KEY=${config.getString("apiKey")}"
                URL(url).readText()
            }
            shutdown()
            println("Server stopped.")
        }
        "status" -> {
            when (running()) {
                true -> println("Server is running!")
                false -> println("Server is not running!")
            }
        }
    }
}