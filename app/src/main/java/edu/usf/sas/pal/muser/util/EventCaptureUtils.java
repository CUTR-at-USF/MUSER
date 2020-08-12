package edu.usf.sas.pal.muser.util;

import android.content.Context;

import com.simplecity.amp_library.ShuttleApplication;
import com.simplecity.amp_library.model.Song;

import edu.usf.sas.pal.muser.firebase.model.Event;
import edu.usf.sas.pal.muser.firebase.model.SongData;

public class EventCaptureUtils {
    public static Event captureEvent(Song song, String capturedEvent, Context context){
        long a = 0;
        long b = 0;
        SongData songData = populateSongData(song, context);
        return new Event(capturedEvent, 0, 0, a,b, songData);
    }

    public static SongData populateSongData(Song song, Context context){
        return new SongData(song.id, song.name, song.artistId, song.albumName, song.albumId
                , song.albumName, song.playlistSongId, song.playlistSongPlayOrder, song.lastPlayed
                , song.track, song.discNumber, song.getBitrateLabel(context), song.getFileSizeLabel()
                , song.isPodcast, song.duration, song.dateAdded, song.year, song.path, song.playCount
                , song.getSampleRateLabel(context), song.getFormatLabel());
    }
}
