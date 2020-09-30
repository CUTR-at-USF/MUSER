package edu.usf.sas.pal.muser.util;

import android.content.Context;
import android.util.Log;

import com.simplecity.amp_library.model.Song;
import com.simplecity.amp_library.playback.MediaManager;
import com.simplecity.amp_library.utils.MusicServiceConnectionUtils;

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
        long seekPositionMs = getPosition();
        return new PlayerEvent(capturedEvent, currentTimeMS, nanoTime, seekPositionMs,
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
        long seekPositionMs = getPosition();
        return new UiEvent(capturedUiAction, currentTimeMS, nanoTime, seekPositionMs,
                songData);
    }

    /**
     * function to get the seek position of the track.
     * @return current seek position. 0 if there's an error
     */
    public static long getPosition() {
        if (MusicServiceConnectionUtils.serviceBinder != null &&
                MusicServiceConnectionUtils.serviceBinder.getService() != null) {
            try {
                return MusicServiceConnectionUtils.serviceBinder.getService().getSeekPosition();
            } catch (final Exception e) {
                Log.e("EventUtils", "getPosition() returned error: " + e.toString());
            }
        }
        return 0;
    }
}
