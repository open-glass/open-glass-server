package de.seine_eloquenz.open_glass.game

interface Game {
    fun isAllowedKey(key: Int): Boolean
}