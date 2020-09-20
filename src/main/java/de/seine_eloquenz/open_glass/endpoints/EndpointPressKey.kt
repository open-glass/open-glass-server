package de.seine_eloquenz.open_glass.endpoints;

import de.seine_eloquenz.open_glass.GameProvider;
import de.seine_eloquenz.open_glass.OpenGlassServer;
import fi.iki.elonen.NanoHTTPD;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class EndpointPressKey implements Endpoint {

    private final Robot robot;
    private final GameProvider gameProvider;

    public EndpointPressKey(GameProvider gameProvider) {
        robot = initializeRobot();
        this.gameProvider = gameProvider;
    }

    @Override
    public NanoHTTPD.Response serve(final Map<String, List<String>> params) {
        if (params.containsKey("key")) {
            char key = params.get("key").get(0).charAt(0);
            if (gameProvider.get().isAllowedKey(key)) {
                return handleKeyPress(key);
            } else {
                return BAD_REQUEST;
            }
        } else {
            return BAD_REQUEST;
        }
    }

    private NanoHTTPD.Response handleKeyPress(char character) {
        if (robot == null) {
            return ERROR;
        } else {
            OpenGlassServer.LOG.info("Logged key " + character);
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(character));
            return OK;
        }
    }

    private Robot initializeRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }
}
