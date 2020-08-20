package edu.usf.sas.pal.muser.model;

import org.jetbrains.annotations.NotNull;

/**
 *  The provider enumerator that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public enum PlayerEventType {
        PLAY,
        PAUSE,
        SKIP,
        REPEAT,
        SEEK
}
