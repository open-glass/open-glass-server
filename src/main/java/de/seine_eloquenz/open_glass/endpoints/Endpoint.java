package de.seine_eloquenz.open_glass.endpoints;

import de.seine_eloquenz.open_glass.OpenGlassServer;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;

import java.util.List;
import java.util.Map;

public interface Endpoint {

    Response BAD_REQUEST = OpenGlassServer.newFixedLengthResponse(
                    Response.Status.BAD_REQUEST,
            NanoHTTPD.MIME_PLAINTEXT, "Bad Request"
    );
    Response OK = OpenGlassServer.newFixedLengthResponse(
            Response.Status.OK,
            NanoHTTPD.MIME_PLAINTEXT, "OK"
    );
    Response ERROR = OpenGlassServer.newFixedLengthResponse(
            Response.Status.INTERNAL_ERROR,
            NanoHTTPD.MIME_PLAINTEXT, "An internal error occurred"
    );

    Response serve(Map<String, List<String>> params);
}
