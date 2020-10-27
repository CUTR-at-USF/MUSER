package edu.usf.sas.pal.muser.util;

import android.support.annotation.Nullable;

import com.simplecity.amp_library.model.Song;
import com.simplecity.amp_library.utils.MusicServiceConnectionUtils;

public class MusicServiceUtils {
    @Nullable
    public static Song getSong() {
        if (com.simplecity.amp_library.utils.MusicServiceConnectionUtils.serviceBinder != null && com.simplecity.amp_library.utils.MusicServiceConnectionUtils.serviceBinder.getService() != null) {
            return com.simplecity.amp_library.utils.MusicServiceConnectionUtils.serviceBinder.getService().getSong();
        }
        return null;
    }

    public static boolean isPlaying(){
        if (MusicServiceConnectionUtils.serviceBinder != null && MusicServiceConnectionUtils.serviceBinder.getService() != null) {
            return MusicServiceConnectionUtils.serviceBinder.getService().isPlaying();
        }
        return false;
    }

}
