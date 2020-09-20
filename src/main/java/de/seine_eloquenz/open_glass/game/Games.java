package de.seine_eloquenz.open_glass.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public enum Games implements Game {

    NONE,
    STAR_CITIZEN('b', 'g', 'n'),

    ;

    final Collection<Character> allowedChars;

    Games(Character... chars) {
        this.allowedChars = new HashSet<>();
        Collections.addAll(allowedChars, chars);
    }

    @Override
    public boolean isAllowedKey(final char character) {
        return allowedChars.contains(character);
    }
}
