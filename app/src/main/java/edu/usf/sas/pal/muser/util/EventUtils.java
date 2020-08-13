package edu.usf.sas.pal.muser.util;

import android.content.Context;

import com.simplecity.amp_library.ShuttleApplication;
import com.simplecity.amp_library.model.Song;

import edu.usf.sas.pal.muser.model.Event;
import edu.usf.sas.pal.muser.model.SongData;



public class EventUtils {

    /**
     * Function to populate the Event data class.
     * @param song - The song for which the event occurred.
     * @param capturedEvent - The event that was captured.
     * @param context - The context of the Fragment.
     * @return - Event object
     */
    public static Event newEvent(Song song, String capturedEvent, Context context){
        long currentTimeMS = System.currentTimeMillis();
        long nanoTime = System.nanoTime();
        SongData songData = newSongData(song, context);
        long elapsedTime =  System.currentTimeMillis() - song.startTime;
        return new Event(capturedEvent, currentTimeMS, nanoTime, song.startTime, elapsedTime, songData);
    }

    /**
     * Function to populate SongData class. Called by captureEvent().
     * @param song - Details of the song.
     * @param context - Context of the fragment.
     * @return - SongData object
     */
    public static SongData newSongData(Song song, Context context){
        return new SongData(song.id, song.name, song.artistId, song.albumName, song.albumId,
                  song.albumName, song.playlistSongId, song.playlistSongPlayOrder, song.lastPlayed,
                  song.track, song.discNumber, song.getBitrateLabel(context), song.getFileSizeLabel(),
                  song.isPodcast, song.duration, song.dateAdded, song.year, song.path, song.playCount,
                  song.getSampleRateLabel(context), song.getFormatLabel());
    }
}
