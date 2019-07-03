package com.example.pushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    TextView txt_title,txt_body,txt_image_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        txt_title = findViewById(R.id.txt_title);
        txt_body = findViewById(R.id.txt_body);
        txt_image_url  =findViewById(R.id.txt_image_url);

        txt_title.setText(App.notifModel.getTitle());
        txt_body.setText(App.notifModel.getBody());
        txt_image_url.setText(App.notifModel.getImage_url());
    }
}
