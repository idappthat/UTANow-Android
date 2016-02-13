package com.mobi.utanow.App.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mobi.utanow.App.Dashboard.DashboardActivity;
import com.mobi.utanow.App.MapBox.MapBoxActivity;
import com.mobi.utanow.myapplication.R;

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
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        context = this;


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        // Check if user is already logged in
        if(AccessToken.getCurrentAccessToken()!=null) {
            Log.d("fb token", AccessToken.getCurrentAccessToken().getToken());
            intent = new Intent(context,DashboardActivity.class);
            startActivity(intent);
        }

        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        skipButton = (Button) findViewById(R.id.skip_login);

        fbLoginButton.setReadPermissions(Arrays.asList("user_groups"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //user logged in correctly
                intent = new Intent(context, DashboardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // User closed login window (make sure)
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(context, "an error has occured please try again", Toast.LENGTH_SHORT);
                Log.e("Loggin Error", exception.getLocalizedMessage());
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, MapBoxActivity.class);
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
