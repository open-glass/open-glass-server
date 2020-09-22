package de.seine_eloquenz.open_glass.endpoints

import de.seine_eloquenz.open_glass.OpenGlassServer
import fi.iki.elonen.NanoHTTPD

interface Endpoint {
    fun serve(params: Map<String, List<String>>): NanoHTTPD.Response?

    companion object StatusCode {

        @JvmField
        val FORBIDDEN: NanoHTTPD.Response = OpenGlassServer.newFixedLengthResponse(
                NanoHTTPD.Response.Status.FORBIDDEN,
                NanoHTTPD.MIME_PLAINTEXT,
                "You're not authenticated"
        )

        @JvmField
        val BAD_REQUEST: NanoHTTPD.Response = OpenGlassServer.newFixedLengthResponse(
                NanoHTTPD.Response.Status.BAD_REQUEST,
                NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request",
        )

        @JvmField
        val OK: NanoHTTPD.Response = OpenGlassServer.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                NanoHTTPD.MIME_PLAINTEXT,
                "OK",
        )

        @JvmField
        val ERROR: NanoHTTPD.Response = OpenGlassServer.newFixedLengthResponse(
                NanoHTTPD.Response.Status.INTERNAL_ERROR,
                NanoHTTPD.MIME_PLAINTEXT,
                "An internal error occurred"
        )
    }
}