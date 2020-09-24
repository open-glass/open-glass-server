package de.seine_eloquenz.open_glass.server.game

interface Game {
    fun isAllowedKey(key: Int): Boolean
}