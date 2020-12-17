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
         PLAY_NEXT,
         PLAY_ALBUM_NEXT,
         PLAY_ALBUM_ARTIST_NEXT,
         PLAY_GENRE_NEXT,
         ADD_TO_PLAYLIST,
         ADD_TO_PLAYLIST_ALBUM,
         ADD_TO_PLAYLIST_ALBUM_ARTIST,
         ADD_TO_PLAYLIST_GENRE,
         ALBUM_SHUFFLE,
         DELETE,
         DELETE_ALBUM,
         DELETE_ALBUM_ARTIST,
         PAUSE,
         NEXT,
         PREV,
         REPEAT_OFF,
         REPEAT_ALL_SONGS,
         REPEAT_CURRENT_SONG,
         SCAN_FORWARD,
         SCAN_BACKWARD,
         FAVORITE,
         UNFAVORITE,
         SEEK_START,
         SEEK_STOP, // seek position for SEEK_STOP will always match SEEK_START in the case of skipping seek positions using the seek bar
         SHUFFLE_ON,
         SHUFFLE_OFF,
         CREATE_PLAYLIST,
         SELECT_CATEGORY,
         SELECT_ARTIST,
         VOLUME_UP,
         VOLUME_DOWN,
         VOLUME_NO_CHANGE
}
