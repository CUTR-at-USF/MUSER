package edu.usf.sas.pal.muser.model

import org.apache.commons.lang3.builder.HashCodeBuilder

data class DeviceInfo(
val appVersion: String,
val deviceModel: String,
val sdkVersion: String,
val sdkVersionInt: Int,
val googlePlayServicesApp: String,
val googlePlayServicesLib: Int,
val regionId: Long,
val isTalkBackEnabled: Boolean,
val timestamp: String,
val isPowerSaveModeEnabled: Boolean?,
val isIgnoringBatteryOptimizations: Boolean?
)

@Override
fun hashCode(deviceInfo: DeviceInfo): Int {
    val builder: HashCodeBuilder = HashCodeBuilder().append(deviceInfo.appVersion).append(deviceInfo.deviceModel).append(deviceInfo.sdkVersion)
            .append(deviceInfo.sdkVersionInt).append(deviceInfo.googlePlayServicesApp).append(deviceInfo.googlePlayServicesLib)
            .append(deviceInfo.regionId).append(deviceInfo.isTalkBackEnabled)
    if (deviceInfo.isPowerSaveModeEnabled != null) {
        builder.append(deviceInfo.isPowerSaveModeEnabled)
    }
    if (deviceInfo.isIgnoringBatteryOptimizations != null) {
        builder.append(deviceInfo.isIgnoringBatteryOptimizations)
    }
    return builder.toHashCode()
}