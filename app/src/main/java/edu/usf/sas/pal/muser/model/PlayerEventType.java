package edu.usf.sas.pal.muser.model;

import org.jetbrains.annotations.NotNull;

/**
 *  The enumerator that stores different types of events that occur while playing
 *  a sound track.
 */
public enum PlayerEventType {
        PLAY,
        PAUSE,
        SKIP,
        REPEAT,
        SEEK
}
