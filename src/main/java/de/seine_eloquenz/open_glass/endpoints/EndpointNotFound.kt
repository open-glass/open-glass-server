package de.seine_eloquenz.open_glass.endpoints

import fi.iki.elonen.NanoHTTPD

class EndpointNotFound : Endpoint {

    override fun serve(params: Map<String, List<String>>): NanoHTTPD.Response {
        return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.NOT_FOUND,
                NanoHTTPD.MIME_PLAINTEXT,
                "Endpoint not found"
        )
    }
}