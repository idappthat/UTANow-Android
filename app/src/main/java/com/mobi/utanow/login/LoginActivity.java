package com.mobi.utanow.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.eventdetails.EventDetailsActivity;
import com.mobi.utanow.eventslist.EventListActivity;
import com.mobi.utanow.models.User;
import com.mobi.utanow.models.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Anthony on 11/13/15.
 */
public class LoginActivity extends AppCompatActivity {
    @Inject
    Firebase mFirebase;
    @Inject
    SharedPreferences mPrefs;

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

        String[] perms = new String[] {"public_profile", "email", "user_photos", "user_events", "user_managed_groups", "user_birthday"};

        fbLoginButton.setReadPermissions(Arrays.asList(perms));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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


        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onFacebookAccessTokenChange(final AccessToken token) {
        if (token != null) {
            mFirebase.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    //user logged in correctly
                    requestUserData(token, authData);
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

    private void requestUserData(final AccessToken token, final AuthData authData) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        User user = new User();
                        UserInfo userInfo = new UserInfo();

                        try {
                            String uuid = authData.getUid();
                            String email = object.getString("email");
                            String name = object.getString("name");
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                            String id = object.getString("id");
                            String imageUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String fbToken = token.getToken();
                            long expires = authData.getExpires();
                            String provider = authData.getProvider();

                            mPrefs.edit().putString("email", email).commit();
                            mPrefs.edit().putString("name", name).commit();
                            mPrefs.edit().putString("picture", imageUrl).commit();
                            mPrefs.edit().putString("uuid", uuid).commit();

                            user.setEmail(email);
                            user.setName(name);
                            user.setProfileImageUrl(imageUrl);

                            userInfo.setName(name);
                            userInfo.setProfileImageUrl(imageUrl);
                            userInfo.setEmail(email);
                            userInfo.setFbId(id);
                            userInfo.setFbToken(fbToken);
                            userInfo.setGender(gender);
                            userInfo.setFbTokenExpires(expires);
                            userInfo.setProvider(provider);
                            userInfo.setBirthday(birthday);

                            mFirebase.child("users").child(uuid).setValue(user);
                            mFirebase.child("userInfo").child(uuid).setValue(userInfo);


                            Intent intent = new Intent(context, EventListActivity.class);
                            startActivity(intent);
                        }
                        catch (JSONException e) {
                            Log.e("LOGIN", "onCompleted: ", e);
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture.type(large),birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call callback manager when login activity is finished
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
