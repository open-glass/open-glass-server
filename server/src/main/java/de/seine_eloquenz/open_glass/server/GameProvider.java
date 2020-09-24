package de.seine_eloquenz.open_glass.server;

import de.seine_eloquenz.open_glass.server.game.Game;

public interface GameProvider {
    Game get();
}
