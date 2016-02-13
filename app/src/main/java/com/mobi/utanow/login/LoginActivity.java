package com.mobi.utanow.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mobi.utanow.R;
import com.mobi.utanow.eventslist.EventListActivity;

import java.util.Arrays;

/**
 * Created by Anthony on 11/13/15.
 */
public class LoginActivity extends AppCompatActivity {

    LoginButton fbLoginButton;
    Button skipButton;
    CallbackManager callbackManager;
    Context context;
    Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        context = this;


        // Check if user is already logged in
        if(AccessToken.getCurrentAccessToken()!=null) {
            Log.d("fb token", AccessToken.getCurrentAccessToken().getToken());
            intent = new Intent(context,EventListActivity.class);
            startActivity(intent);
        }

        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        skipButton = (Button) findViewById(R.id.skip_login);

        fbLoginButton.setReadPermissions(Arrays.asList("user_groups"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //user logged in correctly
                intent = new Intent(context,EventListActivity.class);
                startActivity(intent);

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

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, EventListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call callback manager when login activity is finished
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
