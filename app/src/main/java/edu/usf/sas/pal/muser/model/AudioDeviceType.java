package edu.usf.sas.pal.muser.model;

/**
 * These values MUST be in the order of the variable values with the same name in Android's
 * android.media.AudioDeviceInfo class (https://developer.android.com/reference/android/media/AudioDeviceInfo).
 * For example, on Android TYPE_UNKNOWN has the value of 1, TYPE_BUILTIN_EARPIECE is 2, etc.
 * If enums are added below out-of-order with the values of the Android enums then the incorrect
 * values will be stored within the app when getting information from the AudioManager.
 */
public enum AudioDeviceType {
    TYPE_UNKNOWN,
    TYPE_BUILTIN_EARPIECE,
    TYPE_BUILTIN_SPEAKER,
    TYPE_WIRED_HEADSET,
    TYPE_WIRED_HEADPHONES,
    TYPE_LINE_ANALOG,
    TYPE_LINE_DIGITAL,
    TYPE_BLUETOOTH_SCO,
    TYPE_BLUETOOTH_A2DP,
    TYPE_HDMI,
    TYPE_HDMI_ARC,
    TYPE_USB_DEVICE,
    TYPE_USB_ACCESSORY,
    TYPE_DOCK,
    TYPE_FM,
    TYPE_BUILTIN_MIC,
    TYPE_FM_TUNER,
    TYPE_TV_TUNER,
    TYPE_TELEPHONY,
    TYPE_AUX_LINE,
    TYPE_IP,
    TYPE_BUS,
    TYPE_USB_HEADSET,
    TYPE_HEARING_AID
}
