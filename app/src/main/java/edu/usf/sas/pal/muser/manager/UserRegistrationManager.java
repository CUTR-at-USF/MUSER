package edu.usf.sas.pal.muser.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.simplecity.amp_library.R;
import com.simplecity.amp_library.ShuttleApplication;
import com.simplecity.amp_library.ui.screens.main.MainActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.usf.sas.pal.muser.constants.EventConstants;
import edu.usf.sas.pal.muser.util.FirebaseIOUtils;
import edu.usf.sas.pal.muser.util.PreferenceUtils;

/**
 * Manager class to handle user registration. Creates dialogs to prompt the user about the study
 * fetch  email if user agrees to enroll.
 */
public class UserRegistrationManager {

    private static String TAG = UserRegistrationManager.class.getSimpleName();

    private Context mApplicationContext;

    private Context mActivityContext;

    public UserRegistrationManager(Context applicationContext, Context activityContext){
        mActivityContext = activityContext;
        mApplicationContext = activityContext;
    }

    public void registerParticipant(){
        registerParticipant(false);
    }

    public void registerParticipant(boolean forceStart){
        // TODO check possible limit of enrollment from a certain location?
        boolean isUserOptOut = PreferenceUtils.getBoolean(EventConstants.USER_OPT_OUT,
                false);
        if (forceStart) { isUserOptOut = false; }

        if (isUserOptOut){
            switchToMainActivity(mActivityContext);
            return;
        }

        boolean isUserOptIn = PreferenceUtils.getBoolean(EventConstants.USER_OPT_IN,
                false);
        if ((!isUserOptIn)) {
            showParticipationDialog();
        } else {
            switchToMainActivity(mActivityContext);
        }
    }

    private void showParticipationDialog() {
        View view = LayoutInflater.from(mActivityContext).inflate(R.layout.research_participation_dialog, null);
        CheckBox neverShowDialog = view.findViewById(R.id.research_never_ask_again);

        new AlertDialog.Builder(mActivityContext)
                .setView(view)
                .setTitle(R.string.register_user_opt_in_title)
                .setIcon(createIcon())
                .setCancelable(false)
                .setPositiveButton(R.string.research_user_dialog_yes,
                       (dialogInterface, i) -> showInformedConsent())
                .setNegativeButton(R.string.research_user_dialog_no,
                        (dialogInterface, i) -> {
                            if (neverShowDialog.isChecked()){
                                optOutUser();
                            }
                        })
                .create()
                .show();
    }

    private void optOutUser() {
        PreferenceUtils.saveBoolean(EventConstants.USER_OPT_OUT, true);
        PreferenceUtils.saveBoolean(EventConstants.USER_OPT_IN, false);
    }

    private void showInformedConsent() {
        String consentHtml = getHtmlConsentDocument();
        new AlertDialog.Builder(mActivityContext)
                .setMessage(Html.fromHtml(consentHtml))
                .setTitle(R.string.register_user_opt_in_title)
                .setIcon(createIcon())
                .setCancelable(false)
                .setPositiveButton(R.string.participation_consent_agree,
                        (dialog, which) -> {
                            showEmailDialog();
                        })
                .setNegativeButton(R.string.participation_consent_disagree,
                        (dialog, which) -> {
                            optOutUser();
                        })
                .create().show();
    }

    private String getHtmlConsentDocument() {
        InputStream inputStream = mApplicationContext.getResources().
                openRawResource(R.raw.travel_behavior_informed_consent);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }

    private void showEmailDialog() {
        showEmailDialog(null);
    }

    private void showEmailDialog(String email){
        LayoutInflater inflater = ((Activity) mActivityContext).getLayoutInflater();
        final View editTextView = inflater.inflate(R.layout.research_email_dialog, null);
        EditText emailEditText = editTextView.findViewById(R.id.tb_email_edittext);
        EditText emailEditTextConfirm = editTextView.findViewById(R.id.tb_email_edittext_confirm);

        if (email != null) {
            emailEditText.setText(email);
        }

        new AlertDialog.Builder(mActivityContext)
                .setTitle(R.string.register_user_opt_in_title)
                .setMessage(R.string.research_email_message)
                .setIcon(createIcon())
                .setCancelable(false)
                .setView(editTextView)
                .setPositiveButton(R.string.email_dialog_save,
                        (dialog, which) -> {
                            String currentEmail = emailEditText.getText().toString();
                            String currentEmailConfirm = emailEditTextConfirm.getText().toString();
                            if (!TextUtils.isEmpty(currentEmail) &&
                                    Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches() &&
                                    currentEmail.equalsIgnoreCase(currentEmailConfirm)) {
                                FirebaseIOUtils.registerUser(currentEmail, mActivityContext);
                            } else {
                                Toast.makeText(mApplicationContext, R.string.research_email_invalid,
                                        Toast.LENGTH_LONG).show();
                                // Android automatically dismisses the dialog.
                                // Show the dialog again if the email is invalid
                                showEmailDialog(currentEmail);
                            }
                        })
                .create().show();
    }


    private Drawable createIcon() {
        Drawable icon = mApplicationContext.getResources().getDrawable(R.drawable.ic_launcher_foreground);
        return icon;
    }

    public static void switchToMainActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void optInUser(String uid) {
        PreferenceUtils.saveString(EventConstants.USER_ID, uid);
        PreferenceUtils.saveBoolean(EventConstants.USER_OPT_IN, true);
        PreferenceUtils.saveBoolean(EventConstants.USER_OPT_OUT, false);
    }

    public static Uri buildUri(String uid, String email) {
        return Uri.parse(ShuttleApplication.get().getResources().getString(R.string.
                research_participants_url)).buildUpon().appendQueryParameter("id", uid)
                .appendQueryParameter("email", email).build();
    }


    public static int saveEmailAddress(String uid, String email) throws IOException {
        return saveMapping(buildUri(uid,email));

    }

    public static int saveMapping(Uri uri) throws IOException {
        URL url = new URL(uri.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setReadTimeout(30 * 1000);
        return httpURLConnection.getResponseCode();
    }

}
