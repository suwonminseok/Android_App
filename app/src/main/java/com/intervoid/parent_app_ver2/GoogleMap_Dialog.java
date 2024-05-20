package com.intervoid.parent_app_ver2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class GoogleMap_Dialog extends FragmentActivity implements OnMapReadyCallback, Serializable {

    private GoogleMap GMap;

    private Button finishButton;

    ArrayList GetPeriodicTime = new ArrayList<>();
    ArrayList GetEventTime = new ArrayList<>();

    ArrayList GetTime = new ArrayList<>();

    ArrayList globalHour = new ArrayList<>();
    ArrayList globalMinute = new ArrayList<>();

    float Get_lat = 0;
    float Get_lot = 0;

    String TimeVal = null;

    String gName = null;
    String PeriodicTime = null;

    int LocalHour = 0;
    int LocalMinute = 0;

    int gPosition = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_googlemapdialog);

        SupportMapFragment MapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.DGoogle_Map);
        MapFragment.getMapAsync(this);

        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y=400;
        layoutParams.width = 800;
        layoutParams.height = 1300;

        finishButton = (Button) findViewById(R.id.GoogleMap_finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent getIntent = getIntent();
        gName = getIntent.getStringExtra("Hname");

        GetPeriodicTime = (ArrayList)getIntent.getSerializableExtra("gPeriodicTime");
        GetEventTime = (ArrayList)getIntent().getSerializableExtra("gEventTime");

        Get_lat = getIntent.getFloatExtra("gLat", 0);
        Get_lot = getIntent.getFloatExtra("gLot", 0);
        TimeVal = getIntent.getStringExtra("TimeVal");

        globalHour = (ArrayList)getIntent.getSerializableExtra("globalHour");
        globalMinute = (ArrayList)getIntent.getSerializableExtra("globalMinute");

        gPosition = getIntent.getIntExtra("Position", 0);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GMap = googleMap;
        PeriodicGlobalPosition(Get_lat, Get_lot, TimeVal);

    }

    private void PeriodicGlobalPosition(float lat, float lot, String Timeval) {
        LatLng Location = new LatLng(lat, lot);

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.walkmark);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        Marker WalkMarker = GMap.addMarker(new MarkerOptions()
                .position(Location)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .title(gName+" "+Timeval));

        WalkMarker.showInfoWindow();
        GMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 18));
    }

    private void EventGlobalPosition(float lat, float lot) {
        LatLng location = new LatLng(lat, lot);

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.runmark);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        Marker RunMarker = GMap.addMarker(new MarkerOptions()
        .position(location)
        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        .title(gName/*+" "+EventTime*/));

        RunMarker.showInfoWindow();
        GMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
    }
}
