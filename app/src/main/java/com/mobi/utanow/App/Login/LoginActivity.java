package com.mobi.utanow.App.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobi.utanow.App.MapBox.MapBoxActivity;
import com.mobi.utanow.myapplication.R;

/**
 * Created by Anthony on 11/13/15.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void goToMap(View view){//sorry about testing this way :<
        Intent intent = new Intent(this, MapBoxActivity.class);
        intent.putExtra("xco",(double)32.729);
        intent.putExtra("yco",(double)-97.115);
        startActivity(intent);
    }
}
