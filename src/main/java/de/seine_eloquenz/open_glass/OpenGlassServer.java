package de.seine_eloquenz.open_glass;


import de.seine_eloquenz.open_glass.endpoints.Endpoint;
import de.seine_eloquenz.open_glass.endpoints.EndpointNotFound;
import de.seine_eloquenz.open_glass.endpoints.EndpointPressKey;
import de.seine_eloquenz.open_glass.game.Game;
import de.seine_eloquenz.open_glass.game.Games;
import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class OpenGlassServer extends NanoHTTPD {

    /**
     * logger to log to.
     */
    public static final Logger LOG = Logger.getLogger(OpenGlassServer.class.getName());
    private static final Response FORBIDDEN
            = newFixedLengthResponse(Response.Status.FORBIDDEN, MIME_PLAINTEXT, "You're not authenticated");

    private final String apiKey;
    private final Map<String, Endpoint> endpoints;
    private Game game;
    private final GameProvider gameProvider;

    public OpenGlassServer(final String hostname, final int port, final String apiKey) {
        super(hostname, port);
        this.endpoints = new HashMap<>();
        this.apiKey = apiKey;
        this.game = Games.NONE;
        gameProvider = () -> game;
        this.endpoints.put("/presskey", new EndpointPressKey(gameProvider));
    }

    @Override
    public Response serve(final IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        OpenGlassServer.LOG.info(method + " '" + uri + "' ");
        Map<String, List<String>> params = session.getParameters();
        if (params.get("API_KEY") == null || !apiKey.equals(params.get("API_KEY").get(0))) {
            return FORBIDDEN;
        } else {
            if ("/chooseGame".equals(uri)) {
                if (params.containsKey("game")) {
                    try {
                        this.game = Games.valueOf(params.get("game").get(0));
                        return Endpoint.OK;
                    } catch (IllegalArgumentException e) {
                        return Endpoint.BAD_REQUEST;
                    }
                } else {
                    return Endpoint.BAD_REQUEST;
                }
            }
            return endpoints.getOrDefault(uri, new EndpointNotFound()).serve(params);
        }
    }
}
