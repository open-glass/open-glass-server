package de.seine_eloquenz.open_glass

import de.seine_eloquenz.open_glass.game.Game

interface GameProvider {
    fun get(): Game?
}