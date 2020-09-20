package de.seine_eloquenz.open_glass.endpoints;

import de.seine_eloquenz.open_glass.OpenGlassServer;
import fi.iki.elonen.NanoHTTPD;

import java.util.List;
import java.util.Map;

public class EndpointNotFound implements Endpoint {

    @Override
    public NanoHTTPD.Response serve(final Map<String, List<String>> params) {
        return OpenGlassServer.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Endpoint not found");
    }
}
