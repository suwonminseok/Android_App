package com.intervoid.parent_app_ver2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History_Activity extends AppCompatActivity implements Serializable {

    private ImageButton backbutton;
    private ListView listView;

    ArrayList PeriodicData = new ArrayList<>();
    ArrayList LatData = new ArrayList<>();
    ArrayList LotData = new ArrayList<>();
    ArrayList gPeriodicTime = new ArrayList<>();
    ArrayList gEventTime = new ArrayList<>();

    ArrayList gHour = new ArrayList<>();
    ArrayList gMinute = new ArrayList<>();

    ArrayList G_GPS = new ArrayList<>();

    float Get_lat = 0;
    float Get_lot = 0;

    String TimeVal = null;

    ArrayList GLatData = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle saveInstaceState){
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_history);

        backbutton = (ImageButton) findViewById(R.id.history_backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent getnameIntent = getIntent();
        String getName = getnameIntent.getStringExtra("gName");

        Log.d("HISTORY", getName);

        PeriodicData = (ArrayList)getIntent().getSerializableExtra("PeriodicList");
        LatData = (ArrayList)getIntent().getSerializableExtra("gLAT");
        LotData = (ArrayList)getIntent().getSerializableExtra("gLOT");

        Log.d("HISTORY", PeriodicData.toString());

        gPeriodicTime = (ArrayList)getIntent().getSerializableExtra("gPeriodicTime");
        gEventTime = (ArrayList)getIntent().getSerializableExtra("gEventTime");

        gHour = (ArrayList)getIntent().getSerializableExtra("gHour");
        gMinute = (ArrayList)getIntent().getSerializableExtra("gMinute");

        listView = (ListView) findViewById(R.id.history_listview);
        List<String> list = new ArrayList<>();

        for(int i = 0; i <PeriodicData.size(); i++){
            Log.d("History", "val: "+PeriodicData);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PeriodicData);

            listView.setAdapter(adapter);
            Log.d("History_list", "onCreate: "+adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i = 0; i< LatData.size(); i++){
                    Get_lat = Float.parseFloat(LatData.get(position).toString());
                }

                for(int i = 0; i< LotData.size(); i++){
                    Get_lot = Float.parseFloat(LotData.get(position).toString());
                }

                for(int i = 0; i<gPeriodicTime.size(); i++){
                    TimeVal = gPeriodicTime.get(position).toString();
                }

                Intent locationIntent = new Intent(History_Activity.this, GoogleMap_Dialog.class);
                locationIntent.putExtra("gLat", Get_lat);
                locationIntent.putExtra("gLot", Get_lot);
                locationIntent.putExtra("Hname", getName);
                locationIntent.putExtra("TimeVal", TimeVal);

                locationIntent.putExtra("gEventTime", gEventTime);
                locationIntent.putExtra("globalHour", gHour);
                locationIntent.putExtra("globalMinute", gMinute);
                locationIntent.putExtra("Posigion", position);
                startActivity(locationIntent);
            }
        });
    }

    /*
    @Override
    protected void onStop() {
        super.onStop();
        adapter.clear();
    }

     */

    /*
    @Override
    protected void onPause() {
        super.onPause();
        adapter.clear();
    }

     */
}
