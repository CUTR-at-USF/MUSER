package edu.usf.sas.pal.muser.util

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.util.Log
import edu.usf.sas.pal.muser.model.VolumeData

object HeadphoneUtils {

    private const val TAG = "HeadphoneUtils"

    @JvmStatic
     fun getVolumeData(context: Context): VolumeData? {
         val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
         if (am != null) {
             val volumeLevel = am.getStreamVolume(AudioManager.STREAM_MUSIC)
             val volumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
             var volumeMin = 0
             var volumeLevelDB = Float.MIN_VALUE
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                 volumeLevelDB = am.getStreamVolumeDb(AudioManager.STREAM_MUSIC,
                         volumeLevel, AudioDeviceInfo.TYPE_WIRED_HEADPHONES)
                 volumeMin = am.getStreamMinVolume(AudioManager.STREAM_MUSIC)
             }
             return VolumeData(volumeLevel, volumeMax, volumeMin, volumeLevelDB)
         }
        return null
     }
 }