package edu.usf.sas.pal.muser.model

import org.apache.commons.lang3.builder.HashCodeBuilder

data class DeviceInfo(
val appVersion: String,
val deviceModel: String,
val sdkVersion: String,
val sdkVersionInt: Int,
val googlePlayServicesApp: String,
val googlePlayServicesLib: Int,
val isTalkBackEnabled: Boolean,
val timestamp: String,
val isPowerSaveModeEnabled: Boolean?,
val isIgnoringBatteryOptimizations: Boolean?
) {
    @Override
    override fun hashCode(): Int {
        val builder: HashCodeBuilder = HashCodeBuilder().append(this.appVersion).append(this.deviceModel).append(this.sdkVersion)
                .append(this.sdkVersionInt).append(this.googlePlayServicesApp).append(this.googlePlayServicesLib)
                .append(this.isTalkBackEnabled)
        if (this.isPowerSaveModeEnabled != null) {
            builder.append(this.isPowerSaveModeEnabled)
        }
        if (this.isIgnoringBatteryOptimizations != null) {
            builder.append(this.isIgnoringBatteryOptimizations)
        }
        return builder.toHashCode()
    }
}