package edu.usf.sas.pal.muser.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.simplecity.amp_library.R;
import com.simplecity.amp_library.ShuttleApplication;
import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.manager.DeviceInformationManager;
import edu.usf.sas.pal.muser.manager.UserRegistrationManager;
import edu.usf.sas.pal.muser.model.DeviceInfo;
import edu.usf.sas.pal.muser.model.PlayerEvent;
import edu.usf.sas.pal.muser.model.UiEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseIOUtils {
    private static final String TAG = "FirebaseIO";

    private static String buildDocumentPathByUid(String uid, String folder) {
        return "users/" + uid + "/" + folder;
    }

    private static DocumentReference getFirebaseDocReferenceByUserIDAndRecordId(String userId,
                                                                               String recordId,
                                                                               String folder){
        String path = buildDocumentPathByUid(userId, folder);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        return firebaseFirestore.collection(path).document(recordId);
    }

    public static void savePlayerEvent(PlayerEvent playerEvent){
        String userId = PreferenceUtils.getString(EventConstants.USER_ID);
        String recordID = getRecordID();
        DocumentReference documentReference =
                getFirebaseDocReferenceByUserIDAndRecordId(userId, recordID,
                        EventConstants.FIREBASE_PLAYER_EVENT_FOLDER);

        documentReference.set(playerEvent)
                .addOnCompleteListener(task -> {
                      if (task.isSuccessful()) {
                          Log.d(TAG, "playerEvent saved with ID " + documentReference.getId());
                      } else {
                          logErrorMessage(task.getException(), "playerEvent Save Failed");
                      }
                });
    }

    public static void saveUiEvent(UiEvent uiEvent){
        String userId = PreferenceUtils.getString(EventConstants.USER_ID);
        String recordID = getRecordID();
        DocumentReference documentReference =
                getFirebaseDocReferenceByUserIDAndRecordId(userId, recordID,
                        EventConstants.FIREBASE_UI_EVENT_FOLDER);

        documentReference.set(uiEvent)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "UiEvent saved with ID " + documentReference.getId());
                    } else {
                        logErrorMessage(task.getException(), "UiEvent Save Failed");
                    }
                });
    }

    private static synchronized String getRecordID(){
        long rPrefix = PreferenceUtils.getLong(EventConstants.RECORD_ID, 0);
        String recordID = rPrefix++ + "-" + UUID.randomUUID().toString();
        PreferenceUtils.saveLong(EventConstants.RECORD_ID, rPrefix);
        return recordID;
    }

    public static void logErrorMessage(Exception e, String message) {
        if (e != null) {
            Log.d(TAG, message + e.getMessage());
            e.printStackTrace();
        } else {
            Log.d(TAG, message);
        }
    }

    public static void registerUser(String email, Context context){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase user initialized with id:" + firebaseAuth.getUid());
                        new AsyncTask<Void, Integer, Integer>(){
                            @Override
                            protected Integer doInBackground(Void... voids) {
                                int responseCode = 0;
                                try {
                                     responseCode = UserRegistrationManager
                                            .saveEmailAddress(firebaseAuth.getUid(),
                                            email);
                                } catch (IOException e) {
                                    Log.e(TAG, "doInBackground: "
                                            + Arrays.toString(e.getStackTrace()));
                                }
                                return responseCode;
                            }

                            @Override
                            protected void onPostExecute(Integer responseCode) {
                                if (responseCode == 200) {
                                    Toast.makeText(context,
                                            ShuttleApplication.get()
                                                    .getResources().getString(R.string.toast_enrollment_successful),
                                            Toast.LENGTH_SHORT).show();
                                    UserRegistrationManager
                                            .optInUser(firebaseAuth.getUid());
                                    new DeviceInformationManager(context).saveDeviceInformation();
                                }
                                else{
                                    Toast.makeText(context,
                                            ShuttleApplication.get()
                                                    .getResources().getString(R.string.toast_enrollment_failed),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                        initFirebaseUserWithId(firebaseAuth.getUid());
                        UserRegistrationManager.switchToMainActivity(context);
                    } else {
                        logErrorMessage(task.getException(),
                                "user initialization failed: ");
                    }
                });
    }

    public static void initFirebaseUserWithId(String userId){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("users/")
                                              .document(userId + "/");
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        documentReference.set(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Firebase initialized with user id: " + userId);
            } else {
                logErrorMessage(task.getException(),
                        "Firebase failed to initialize with user id");
            }
        });
    }

    public static void saveDeviceInfo(DeviceInfo deviceInfo, String userId,
                                      String recordId, int hashCode) {
        DocumentReference document = FirebaseIOUtils.
                getFirebaseDocReferenceByUserIDAndRecordId(userId, recordId,
                        EventConstants.FIREBASE_DEVICE_INFO_FOLDER);

        document.set(deviceInfo).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Device Info document added with ID " + document.getId());
                PreferenceUtils.saveInt(EventConstants.DEVICE_INFO_HASH, hashCode);
            } else {
                logErrorMessage(task.getException(),
                        "Device Info transition document failed to be added: ");
            }
        });
    }

}
