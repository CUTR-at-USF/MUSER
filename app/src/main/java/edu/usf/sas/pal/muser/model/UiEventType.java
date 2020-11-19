package edu.usf.sas.pal.muser.model;

/**
 * The enumerator that stores different types of user interface events triggered by the user
 * on a sound track.
 */
public enum UiEventType {
         PLAY,
         PLAY_ALBUM,
         PLAY_ALBUM_ARTIST,
         PLAY_GENRE,
         PAUSE,
         NEXT,
         PREV,
         REPEAT_OFF,
         REPEAT_ALL_SONGS,
         REPEAT_CURRENT_SONG,
         SCAN_FORWARD,
         FAVORITE,
         UNFAVORITE,
         SEEK_START,
         SEEK_STOP,
         SHUFFLE_ON,
         SHUFFLE_OFF,
         CREATE_PLAYLIST,
         SELECT_CATEGORY,
         SELECT_ARTIST
}
