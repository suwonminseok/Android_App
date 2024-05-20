package com.intervoid.parent_app_ver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Setting_Activity extends AppCompatActivity {

    private ImageButton BackButton;

    private Button Setting_button;
    private Button Setting_alram;
    private Button Setting_Account_Management;
    private Button Setting_Terms_of_Service;
    private Button Setting_service_center_call;
    private Button Setting_appendbutton;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BackButton = (ImageButton) findViewById(R.id.setting_backbutton);
        Setting_button = (Button) findViewById(R.id.child_append_button);
        Setting_alram = (Button) findViewById(R.id.setting_arlambutton);
        Setting_Account_Management = (Button) findViewById(R.id.Setting_Account_Management);
        Setting_Terms_of_Service = (Button) findViewById(R.id.Setting_Terms_of_Service);
        Setting_service_center_call = (Button) findViewById(R.id.Setting_service_center_call);
        Setting_appendbutton = (Button) findViewById(R.id.child_append_button);

        sharedPreferences = getSharedPreferences("child", MODE_PRIVATE);

        Intent getIntent = getIntent();

        int Get_children = getIntent.getExtras().getInt("number_of_Children");

        Setting_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(Setting_Activity.this, Alarm_Activity.class);
                alarmIntent.putExtra("get_child", Get_children);
                startActivity(alarmIntent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Setting_Account_Management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(Setting_Activity.this, Account_management_Activity.class);
                startActivity(accountIntent);
            }
        });

        Setting_appendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appendIntent = new Intent(Setting_Activity.this, Append_child_Activity.class);
                startActivity(appendIntent);
            }
        });

        Setting_service_center_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = "tel:07041610721";
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
                startActivity(callIntent);
            }
        });

        Setting_Terms_of_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent term_of_service = new Intent(Setting_Activity.this, Terms_of_service_Activity.class);
                startActivity(term_of_service);
            }
        });


    }
}
