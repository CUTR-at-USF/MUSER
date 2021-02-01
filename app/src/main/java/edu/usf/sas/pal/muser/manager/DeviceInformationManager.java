/*
 * Copyright (C) 2021 University of South Florida
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.sas.pal.muser.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.view.accessibility.AccessibilityManager;

import com.google.android.gms.common.GoogleApiAvailability;
import com.simplecity.amp_library.ShuttleApplication;

import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.model.DeviceInfo;
import edu.usf.sas.pal.muser.util.FirebaseIOUtils;
import edu.usf.sas.pal.muser.util.PreferenceUtils;

import static android.content.Context.ACCESSIBILITY_SERVICE;

/** Manager class to collect the device information after the user registration is complete.
 *
 */
public class DeviceInformationManager {

    private Context applicationContext;

    public DeviceInformationManager(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Function that saves the device information. Derived from
     * https://github.com/OneBusAway/onebusaway-android/blob/master/onebusaway-android/src/main/java/org/onebusaway/android/travelbehavior/io/worker/UpdateDeviceInfoWorker.java#L54
     */
    public void saveDeviceInformation() {
        PackageManager pm = applicationContext.getPackageManager();
        PackageInfo appInfoMuser;
        PackageInfo appInfoGps;
        String muserVersion = "";
        String googlePlayServicesAppVersion = "";
        try {
            appInfoMuser = pm.getPackageInfo(applicationContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            muserVersion = appInfoMuser.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Leave version as empty string
        }
        try {
            appInfoGps = pm.getPackageInfo(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            googlePlayServicesAppVersion = appInfoGps.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Leave version as empty string
        }

        AccessibilityManager am = (AccessibilityManager) applicationContext.getSystemService(ACCESSIBILITY_SERVICE);
        Boolean isTalkBackEnabled = am.isTouchExplorationEnabled();

        PowerManager powerManager = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
        Boolean isPowerSaveModeActive = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isPowerSaveModeActive = powerManager.isPowerSaveMode();
        }
        Boolean isIgnoringBatteryOptimizations = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isIgnoringBatteryOptimizations = ShuttleApplication.isIgnoringBatteryOptimizations(applicationContext);
        }

        String recordId = Long.toString(System.currentTimeMillis());

        DeviceInfo di = new DeviceInfo(muserVersion, Build.MODEL, Build.VERSION.RELEASE,
                Build.VERSION.SDK_INT, googlePlayServicesAppVersion,
                GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE, isTalkBackEnabled, recordId,
                isPowerSaveModeActive, isIgnoringBatteryOptimizations);
        int hashCode = di.hashCode();
        int mostRecentDeviceHash = PreferenceUtils.getInt(EventConstants.DEVICE_INFO_HASH,
                -1);

        String uid = PreferenceUtils.getString(EventConstants.USER_ID);

        // Update if the device info changed
        if (hashCode != mostRecentDeviceHash && uid != null) {
            FirebaseIOUtils.saveDeviceInfo(di, uid, recordId, hashCode);
        }
    }
}

