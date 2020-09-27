package de.seine_eloquenz.open_glass.server.game

/**
 * Represents a supported Game
 */
interface Game {

    /**
     * Returns whether the given key code is allowed to be executed in this game
     */
    fun isAllowedKey(key: Int): Boolean
}