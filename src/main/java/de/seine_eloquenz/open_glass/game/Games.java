package de.seine_eloquenz.open_glass.game;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public enum Games implements Game {

    //TODO Make these keybindings configurable in the future
    //We're using default values for simplicities sake for now
    NONE,
    STAR_CITIZEN(
            KeyEvent.VK_B, //Quantum
            KeyEvent.VK_N, //Landing Gear
            KeyEvent.VK_G, //Gimbals
            KeyEvent.VK_P, //Weapons power
            KeyEvent.VK_O, //Shields power
            KeyEvent.VK_I //Engines power
    ),
    ;

    final Collection<Integer> allowedKeys;

    Games(Integer... keys) {
        this.allowedKeys = new HashSet<>();
        Collections.addAll(allowedKeys, keys);
    }

    @Override
    public boolean isAllowedKey(final int key) {
        return allowedKeys.contains(key);
    }
}
