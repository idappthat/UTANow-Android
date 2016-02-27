package com.mobi.utanow.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.eventdetails.EventDetailsActivity;
import com.mobi.utanow.eventslist.EventListActivity;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by Anthony on 11/13/15.
 */
public class LoginActivity extends AppCompatActivity {


    @Inject
    Firebase mFirebase;

    LoginButton fbLoginButton;
    Button skipButton;
    CallbackManager callbackManager;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((UtaNow) getApplication()).getAppComponent().inject(this);


        callbackManager = CallbackManager.Factory.create();
        context = this;


        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        skipButton = (Button) findViewById(R.id.skip_login);

        String[] perms = new String[] {"public_profile", "email", "user_photos"};
        fbLoginButton.setReadPermissions(Arrays.asList(perms));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onFacebookAccessTokenChange(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // User closed login window (make sure)
            }

            @Override
            public void onError(FacebookException exception) {
                Snackbar.make(fbLoginButton, "Login Error Please Try Again", Snackbar.LENGTH_LONG);
                Log.e("Login Error", exception.getLocalizedMessage());
            }
        });


        skipButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, EventListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            mFirebase.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    //user logged in correctly
                    Intent intent = new Intent(context, EventListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Snackbar.make(fbLoginButton, "Login Error Please Try Again", Snackbar.LENGTH_LONG);
                    Log.e("Login Error", firebaseError.getMessage());
                }
            });
        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            mFirebase.unauth();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call callback manager when login activity is finished
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
