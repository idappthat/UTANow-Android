package com.mobi.utanow.eventdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobi.utanow.R;

public class CommentsActivity extends AppCompatActivity {

    Intent intent;
    TextView title;
    EditText comment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        title = (TextView)findViewById(R.id.tv_title);

        title.setText(intent.getStringExtra("eventTitle"));

        comment = (EditText)findViewById(R.id.comment_text);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0){
                    fab.show();
                }else{
                    fab.hide();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK,null);
                finish();
            }
        });

    }

}
