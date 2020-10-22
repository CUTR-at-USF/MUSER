package edu.usf.sas.pal.muser.util;

import android.support.annotation.Nullable;

import com.simplecity.amp_library.model.Song;
import com.simplecity.amp_library.utils.MusicServiceConnectionUtils;

public class SongUtils {
    @Nullable
    public static Song getSong() {
        if (MusicServiceConnectionUtils.serviceBinder != null && MusicServiceConnectionUtils.serviceBinder.getService() != null) {
            return MusicServiceConnectionUtils.serviceBinder.getService().getSong();
        }
        return null;
    }

}
