package de.seine_eloquenz.open_glass.server.game

import java.awt.event.KeyEvent

/**
 * Enum of all supported Games
 */
enum class Games(vararg keys: Int) : Game {
    //TODO Make these keybindings configurable in the future
    //We're using default values for simplicity's sake for now
    NONE,
    STAR_CITIZEN(
            KeyEvent.VK_B, //Quantum
            KeyEvent.VK_N, //Landing Gear
            KeyEvent.VK_G, //Gimbals
            KeyEvent.VK_P, //Weapons power
            KeyEvent.VK_O, //Shields power
            KeyEvent.VK_I,  //Engines power
            KeyEvent.VK_1, //Toggle lock pinned target
            KeyEvent.VK_2, //Toggle lock pinned target
            KeyEvent.VK_3, //Toggle lock pinned target
            KeyEvent.VK_Y, //Leave Pilot seat / Eject (Hold)
            KeyEvent.VK_BACK_SPACE // respawn/suicide / self destruct (Hold)
    ),
    ;

    private val allowedKeys: Collection<Int>

    override fun isAllowedKey(key: Int): Boolean {
        return allowedKeys.contains(key)
    }

    init {
        allowedKeys = keys.toSet()
    }
}