package de.seine_eloquenz.open_glass.server.endpoints

import de.seine_eloquenz.open_glass.server.GameProvider
import fi.iki.elonen.NanoHTTPD

/**
 * Endpoint handling server status requests
 */
class EndpointStatus(private val gameProvider: GameProvider) : Endpoint {

    override fun serve(params: Map<String, List<String>>): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse(
                """
                    {
                        "status": "running",
                        "game": "${gameProvider.get()}"
                    }
                """.trimIndent()
        )
    }

}