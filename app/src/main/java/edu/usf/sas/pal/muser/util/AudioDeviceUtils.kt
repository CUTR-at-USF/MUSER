package edu.usf.sas.pal.muser.util

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.util.Log
import edu.usf.sas.pal.muser.model.AudioData
import edu.usf.sas.pal.muser.model.AudioDeviceType
import edu.usf.sas.pal.muser.model.VolumeData

object AudioDeviceUtils {

    private const val TAG = "HeadphoneUtils"

    @JvmStatic
    fun getAudioData(context: Context): AudioData {
        val audioDeviceType: AudioDeviceType = if (isSpeakerOn(context).second) {
            AudioDeviceType.TYPE_BUILTIN_SPEAKER
        } else {
            AudioDeviceType.values()[isSpeakerOn(context).first]
        }
        val volumeData = getVolumeData(context)
        return AudioData(audioDeviceType, volumeData)
    }

    /**
     * Function to populate volume data class.
     * @param context - context of the activity/fragment.
     * @return VolumeData object.
     */
    @JvmStatic
     fun getVolumeData(context: Context): VolumeData {
         val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
         if (audioManager != null) {
             val volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
             val volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
             var volumeMin = Int.MIN_VALUE
             var volumeLevelDB = Float.MIN_VALUE
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                 volumeMin = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
                 volumeLevelDB = if (isSpeakerOn(context).second) {
                     audioManager.getStreamVolumeDb(AudioManager.STREAM_MUSIC,
                             volumeLevel, AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)
                 } else {
                     audioManager.getStreamVolumeDb(AudioManager.STREAM_MUSIC,
                             volumeLevel, isSpeakerOn(context).first)
                 }
             }
             return VolumeData(volumeLevel, volumeMax, volumeMin, volumeLevelDB)
         }
        return VolumeData()
     }

    /**
     * Function to check if the built in speaker is used or not
     * @param context - context of the activity/fragment
     * @return Pair(deviceType, isSpeakerOn)
     */
    @JvmStatic
    fun isSpeakerOn(context: Context): Pair<Int, Boolean> {
        val headsetTypes: Array<Int>
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        var (deviceType, isSpeakerOn) = Pair(0, true)
        if (audioManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //  AudioDeviceInfo.TYPE_USB_HEADSET is only supported for devices with API_LEVEL >= 26
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
            } else {
                isSpeakerOn = !audioManager.isWiredHeadsetOn && !audioManager.isBluetoothA2dpOn
                        && !audioManager.isBluetoothScoOn
            }
        }
        return Pair(deviceType, isSpeakerOn)
    }
 }
