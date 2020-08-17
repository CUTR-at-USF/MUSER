package edu.usf.sas.pal.muser.util;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.model.Event;

public class EventFirebaseIOUtils {
    private static final String TAG = "EventFirebaseIO";

    private static String buildDocumentPathByUid(String uid, String folder) {
        return "users/" + uid + "/" + folder;
    }

    private static DocumentReference getFirebaseDocReferenceByUserIDAndRecordId(String userId,
                                                                               String recordId,
                                                                               String folder){
        String path = buildDocumentPathByUid(userId, folder);
        FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
        return firebaseFirestore.collection(path).document(recordId);
    }

    public static void saveEvent(Event event, String userId, String recordID){
        DocumentReference documentReference =
                getFirebaseDocReferenceByUserIDAndRecordId(userId, recordID,
                        EventConstants.FIREBASE_EVENT_FOLDER);

        documentReference.set(event)
                .addOnCompleteListener(task -> {
                      if (task.isSuccessful()){
                          Log.d(TAG, "Event saved with ID " + documentReference.getId());
                      }
                      else {
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
}
