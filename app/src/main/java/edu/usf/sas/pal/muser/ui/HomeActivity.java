package edu.usf.sas.pal.muser.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simplecity.amp_library.R;

import edu.usf.sas.pal.muser.manager.UserRegistrationManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new UserRegistrationManager(getApplicationContext(), this)
                .registerParticipant();
    }
}