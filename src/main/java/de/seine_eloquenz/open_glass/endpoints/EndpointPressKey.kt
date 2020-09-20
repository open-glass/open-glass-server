package de.seine_eloquenz.open_glass.endpoints

import de.seine_eloquenz.open_glass.GameProvider
import de.seine_eloquenz.open_glass.OpenGlassServer
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.AWTException
import java.awt.Robot
import java.awt.event.KeyEvent

class EndpointPressKey(gameProvider: GameProvider) : Endpoint {
    private val robot: Robot?
    private val gameProvider: GameProvider
    override fun serve(params: Map<String, List<String>>): NanoHTTPD.Response {
        return if (params.containsKey("key")) {
            val key = params.getValue("key")[0][0]
            if (gameProvider.get().isAllowedKey(key)) {
                handleKeyPress(key)
            } else {
                Endpoint.BAD_REQUEST
            }
        } else {
            Endpoint.BAD_REQUEST
        }
    }

    private fun handleKeyPress(character: Char): NanoHTTPD.Response {
        return if (robot == null) {
            Endpoint.ERROR
        } else {
            OpenGlassServer.LOG.info("Logged key $character")
            pressKey('w')
            Endpoint.OK
        }
    }

    private fun pressKey(character: Char) {
        robot!!
        GlobalScope.launch {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(character.toInt()))
            robot.delay(100)
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(character.toInt()))
        }
    }

    private fun initializeRobot(): Robot? {
        return try {
            Robot()
        } catch (e: AWTException) {
            e.printStackTrace()
            null
        }
    }

    init {
        robot = initializeRobot()
        this.gameProvider = gameProvider
    }
}