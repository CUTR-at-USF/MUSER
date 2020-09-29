package edu.usf.sas.pal.muser.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.model.PlayerEvent;
import edu.usf.sas.pal.muser.model.UiEvent;

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
        } else {
            Log.d(TAG, message);
        }
    }

    public static void registerUser(String email){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase user initialized with id:" + firebaseAuth.getUid());
                        // TODO save email address to Google APP Scripts
                        PreferenceUtils.saveString(EventConstants.USER_ID, firebaseAuth.getUid());
                        initFirebaseUserWithId(firebaseAuth.getUid());
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

}
