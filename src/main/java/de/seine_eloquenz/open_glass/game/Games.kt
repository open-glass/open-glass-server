package de.seine_eloquenz.open_glass.game

import java.awt.event.KeyEvent

enum class Games(vararg keys: Int) : Game {
    //TODO Make these keybindings configurable in the future
    //We're using default values for simplicities sake for now
    NONE,
    STAR_CITIZEN(
            KeyEvent.VK_B, //Quantum
            KeyEvent.VK_N, //Landing Gear
            KeyEvent.VK_G, //Gimbals
            KeyEvent.VK_P, //Weapons power
            KeyEvent.VK_O, //Shields power
            KeyEvent.VK_I  //Engines power
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