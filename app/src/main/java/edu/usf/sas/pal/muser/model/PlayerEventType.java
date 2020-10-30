package edu.usf.sas.pal.muser.model;

import org.jetbrains.annotations.NotNull;

/**
 *  The enumerator that stores different types of events that occur in the music player that
 *  impact the song the user is hearing
 */
public enum PlayerEventType {
        PLAY,
        PAUSE,
        NEXT,
        REPEAT,
        SEEK,
        PREV
}
