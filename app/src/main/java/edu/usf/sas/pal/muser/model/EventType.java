package edu.usf.sas.pal.muser.model;

import org.jetbrains.annotations.NotNull;

/**
 *  The provider enumerator that stores different types of action that can be performed by the user
 *  on a sound track.
 */
public enum EventType {

        PLAY,
        PLAY_MANUAL,
        PAUSE,
        PAUSE_MANUAL,
        SKIP,
        REPEAT,
        SEEK
}
