package edu.usf.sas.pal.muser.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.model.Event;

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

    public static void saveEvent(Event event, String userId, String recordID){
        DocumentReference documentReference =
                getFirebaseDocReferenceByUserIDAndRecordId(userId, recordID,
                        EventConstants.FIREBASE_EVENT_FOLDER);

        documentReference.set(event)
                .addOnCompleteListener(task -> {
                      if (task.isSuccessful()) {
                          Log.d(TAG, "Event saved with ID " + documentReference.getId());
                      } else {
                          logErrorMessage(task.getException(), "Event Save Failed");
                      }
                });
    }

    public static void logErrorMessage(Exception e, String message) {
        if (e != null) {
            Log.d(TAG, message +
                    e.getMessage());
            e.printStackTrace();
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
