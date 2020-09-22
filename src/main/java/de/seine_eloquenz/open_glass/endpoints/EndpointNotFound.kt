package de.seine_eloquenz.open_glass.endpoints

import de.seine_eloquenz.open_glass.OpenGlassServer
import fi.iki.elonen.NanoHTTPD

class EndpointNotFound : Endpoint {

    override fun serve(params: Map<String, List<String>>): NanoHTTPD.Response {
        return OpenGlassServer.newFixedLengthResponse(
                NanoHTTPD.Response.Status.NOT_FOUND,
                NanoHTTPD.MIME_PLAINTEXT,
                "de.seine_eloquenz.open_glass.endpoints.Endpoint not found"
        )
    }
}