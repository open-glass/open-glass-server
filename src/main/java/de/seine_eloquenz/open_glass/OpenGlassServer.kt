package de.seine_eloquenz.open_glass

import de.seine_eloquenz.open_glass.endpoints.Endpoint
import de.seine_eloquenz.open_glass.endpoints.EndpointKey.Factory.endpointKeyHold
import de.seine_eloquenz.open_glass.endpoints.EndpointKey.Factory.endpointKeyPress
import de.seine_eloquenz.open_glass.endpoints.EndpointNotFound
import de.seine_eloquenz.open_glass.game.Game
import de.seine_eloquenz.open_glass.game.Games
import fi.iki.elonen.NanoHTTPD
import java.util.*
import java.util.logging.Logger

class OpenGlassServer(hostname: String?, port: Int, apiKey: String) : NanoHTTPD(hostname, port) {

    companion object {

        /**
         * logger to log to.
         */
        @JvmField
        val LOG: Logger = Logger.getLogger(OpenGlassServer::class.java.name)
    }

    private val apiKey: String
    private val endpoints: MutableMap<String, Endpoint>
    private var game: Game
    private val gameProvider: GameProvider
    private var stop: Boolean = false

    init {
        endpoints = HashMap()
        this.apiKey = apiKey
        game = Games.NONE
        gameProvider = GameProvider { game }
        endpoints["/presskey"] = endpointKeyPress(gameProvider)
        endpoints["/holdkey"] = endpointKeyHold(gameProvider, 10000)
    }

    override fun serve(session: IHTTPSession): Response {
        val method = session.method
        val uri = session.uri
        LOG.info("$method '$uri' ")
        val params = session.parameters
        return if (params["API_KEY"] == null || apiKey != params["API_KEY"]!![0]) {
            Endpoint.FORBIDDEN
        } else {
            return when (uri) {
                "/chooseGame" -> {
                    if (params.containsKey("game")) {
                        try {
                            game = Games.valueOf(params["game"]!![0])
                            Endpoint.OK
                        } catch (e: IllegalArgumentException) {
                            Endpoint.BAD_REQUEST
                        }
                    } else {
                        Endpoint.BAD_REQUEST
                    }
                }
                "/shutdown" -> {
                    stop = true
                    LOG.info("Received shutdown signal. Will shutdown on next Main tick.")
                    Endpoint.OK
                }
                else -> {
                    endpoints.getOrDefault(uri, EndpointNotFound()).serve(params)
                }
            }

        }
    }

    fun shallStop(): Boolean = stop
}