package com.androidapps.swathi.communityhelpapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Swathi on 5/12/2016.
 */
public class Login extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 9001;
    public static String USER_NAME = new String();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        /*viewPager = (ViewPager) findViewById(R.id.slideshow);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);*/
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {

    }

    public void signOut(View view) {
        final TextView textView = (TextView) findViewById(R.id.mStatusTextView);
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        textView.setText("Logged out successfully!!");
                        // [END_EXCLUDE]
                    }
                });
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.sign_in_button) {
            //mGoogleApiClient.connect();
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        TextView mStatusTextView = (TextView) findViewById(R.id.mStatusTextView);
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Login.USER_NAME = acct.getDisplayName();
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            mStatusTextView.setText("Invalid user credentials");
            //updateUI(false);
        }
    }
}
