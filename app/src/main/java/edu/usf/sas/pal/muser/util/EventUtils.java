package edu.usf.sas.pal.muser.util;

import android.content.Context;

import com.simplecity.amp_library.ShuttleApplication;
import com.simplecity.amp_library.model.Song;

import edu.usf.sas.pal.muser.model.Event;
import edu.usf.sas.pal.muser.model.EventType;
import edu.usf.sas.pal.muser.model.SongData;



public class EventUtils {

    /**
     * Function to populate the Event data class.
     * @param song - The song for which the event occurred.
     * @param capturedEvent - The event that was captured.
     * @param context - The context of the Fragment.
     * @return - Event object
     */
    public static Event newEvent(Song song, EventType capturedEvent, Context context){
        long currentTimeMS = System.currentTimeMillis();
        long nanoTime = System.nanoTime();
        SongData songData = new SongData(song, context);
        long elapsedTime =  song.getElapsedTime();
        return new Event(capturedEvent, currentTimeMS, nanoTime, song.startTime, elapsedTime, songData);
    }
}
