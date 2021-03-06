package de.seine_eloquenz.open_glass.server

import de.seine_eloquenz.open_glass.server.endpoints.Endpoint
import de.seine_eloquenz.open_glass.server.endpoints.EndpointKey.Factory.endpointKeyHold
import de.seine_eloquenz.open_glass.server.endpoints.EndpointKey.Factory.endpointKeyPress
import de.seine_eloquenz.open_glass.server.endpoints.EndpointNotFound
import de.seine_eloquenz.open_glass.server.endpoints.EndpointStatus
import de.seine_eloquenz.open_glass.server.game.Game
import de.seine_eloquenz.open_glass.server.game.Games
import fi.iki.elonen.NanoHTTPD
import java.util.*
import java.util.logging.Logger

/**
 * Main Server class handling HTTP requests
 */
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
        endpoints["/holdkey"] = endpointKeyHold(gameProvider, 3000)
        endpoints["/status"] = EndpointStatus(gameProvider)
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

    /**
     * Returns whether the server received a shutdown signal and is about to stop
     */
    fun shallStop(): Boolean = stop
}