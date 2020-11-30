package edu.usf.sas.pal.muser.util

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.util.Log
import edu.usf.sas.pal.muser.model.VolumeData

object HeadphoneUtils {

    private const val TAG = "HeadphoneUtils"
    private var deviceType = 0
    @JvmStatic
     fun getVolumeData(context: Context): VolumeData? {
         val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
         if (audioManager != null) {
             val volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
             val volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
             var volumeMin = Int.MIN_VALUE
             var volumeLevelDB = Float.MIN_VALUE
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                 volumeMin = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
                 volumeLevelDB = if (isSpeakerOn(context)) {
                     audioManager.getStreamVolumeDb(AudioManager.STREAM_MUSIC,
                             volumeLevel, AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)
                 } else{
                     audioManager.getStreamVolumeDb(AudioManager.STREAM_MUSIC,
                             volumeLevel, deviceType)
                 }
             }
             return VolumeData(volumeLevel, volumeMax, volumeMin, volumeLevelDB)
         }
        return null
     }

    @JvmStatic
    fun isSpeakerOn(context: Context): Boolean{
        val headsetTypes: Array<Int>
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        var isSpeakerOn = true
        if (audioManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //  AudioDeviceInfo.TYPE_USB_HEADSET is only supported for devices with API_LEVEL > 26
                headsetTypes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    arrayOf(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP, AudioDeviceInfo.TYPE_BLUETOOTH_SCO,
                            AudioDeviceInfo.TYPE_WIRED_HEADSET, AudioDeviceInfo.TYPE_WIRED_HEADPHONES,
                            AudioDeviceInfo.TYPE_USB_HEADSET, AudioDeviceInfo.TYPE_USB_DEVICE)
                } else {
                    arrayOf(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP, AudioDeviceInfo.TYPE_BLUETOOTH_SCO,
                            AudioDeviceInfo.TYPE_WIRED_HEADSET, AudioDeviceInfo.TYPE_WIRED_HEADPHONES,
                            AudioDeviceInfo.TYPE_USB_DEVICE)
                }
                val audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
                if (audioDevices != null) {
                    for (audioDevice in audioDevices) {
                        Log.d(TAG, "isSpeakerOn: ${audioDevice.type}")
                        if (headsetTypes.contains(audioDevice.type)) {
                            deviceType = audioDevice.type
                            isSpeakerOn = false
                            break
                        }
                    }
                }
            }
        }
        return isSpeakerOn
    }

 }