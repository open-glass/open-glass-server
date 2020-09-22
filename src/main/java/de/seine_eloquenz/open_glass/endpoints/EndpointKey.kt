package de.seine_eloquenz.open_glass.endpoints

import de.seine_eloquenz.open_glass.GameProvider
import de.seine_eloquenz.open_glass.OpenGlassServer
import fi.iki.elonen.NanoHTTPD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.AWTException
import java.awt.Robot
import java.awt.event.KeyEvent

class EndpointKey private constructor(gameProvider: GameProvider, duration: Int) : Endpoint {

    companion object Factory {
        private const val KEY_PRESS_DURATION: Int = 100

        /**
         * Creates an Endpoint handling a key press event
         */
        fun endpointKeyPress(gameProvider: GameProvider): EndpointKey {
            return EndpointKey(gameProvider, KEY_PRESS_DURATION)
        }

        /**
         * Creates an Endpoint handling a key hold event
         */
        fun endpointKeyHold(gameProvider: GameProvider, duration: Int): EndpointKey {
            return EndpointKey(gameProvider, duration)
        }
    }

    private val robot: Robot?
    private val duration: Int
    private val gameProvider: GameProvider

    init {
        robot = initializeRobot()
        this.gameProvider = gameProvider
        this.duration = duration
    }

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
            pressKey(character)
            Endpoint.OK
        }
    }

    private fun pressKey(character: Char) {
        robot!!
        GlobalScope.launch {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(character.toInt()))
            robot.delay(duration)
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
}