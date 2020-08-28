package edu.usf.sas.pal.muser.util;

import android.content.Context;

import com.simplecity.amp_library.model.Song;

import edu.usf.sas.pal.muser.model.PlayerEvent;
import edu.usf.sas.pal.muser.model.PlayerEventType;
import edu.usf.sas.pal.muser.model.SongData;
import edu.usf.sas.pal.muser.model.UiEvent;
import edu.usf.sas.pal.muser.model.UiEventType;


public class EventUtils {

    /**
     * Function to populate the PlayerEvent data class.
     * @param song - The song for which the event occurred.
     * @param capturedEvent - The event that was captured.
     * @param context - The context of the Fragment.
     * @return PlayerEvent object
     */
    public static PlayerEvent newPlayerEvent(Song song, PlayerEventType capturedEvent, Context context){
        long currentTimeMS = System.currentTimeMillis();
        long nanoTime = System.nanoTime();
        SongData songData = new SongData(song, context);
        return new PlayerEvent(capturedEvent, currentTimeMS, nanoTime,
                         songData);
    }

    /**
     * Function to populate the UiAction data class
     * @param song - The song for which the action was performed.
     * @param capturedUiAction - The action that was captured
     * @param context - The context of the fragment.
     * @return UiAction object
     */
    public static UiEvent newUiEvent(Song song, UiEventType capturedUiAction, Context context){
        long currentTimeMS = System.currentTimeMillis();
        long nanoTime = System.nanoTime();
        SongData songData = new SongData(song, context);
        return new UiEvent(capturedUiAction, currentTimeMS, nanoTime,
                songData);
    }
}
