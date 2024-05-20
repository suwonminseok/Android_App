package com.intervoid.parent_app_ver2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.GetChars;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.ArrayList;

public class GoogleMap_ShowPath extends FragmentActivity implements OnMapReadyCallback, Serializable {

    private GoogleMap gMap;
    private Button finishButton;

    ArrayList<ArrayList> GetPeriodic = new ArrayList<ArrayList>();
    ArrayList GLAT = new ArrayList();

    View marker_root_view;
    private TextView tv_marker;

    private LatLng FirstLocation = null;
    private LatLng LastLocation = null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_googlemapshowpath);

        SupportMapFragment MapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.SGoogle_Map);
        MapFragment.getMapAsync(this);

        setCustomMarker();

        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y = 400;
        layoutParams.width = 800;
        layoutParams.height = 1300;

        finishButton = (Button) findViewById(R.id.Showpath_finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent GetGps = getIntent();
        GetPeriodic = (ArrayList)GetGps.getSerializableExtra("GPS_Value");

        Log.d("ShowPath", GetPeriodic.toString());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        ShowPath(GetPeriodic);
    }

    private void ShowPath(ArrayList<ArrayList> GpsPath){

        LatLng Zlocation = null;
        int ZoomVal = 0;
        double Distance =0;

        for(int i = 0 ; i < GpsPath.size(); i++){

            LatLng Lposition = new LatLng(Double.parseDouble(GpsPath.get(i).get(0).toString()), Double.parseDouble(GpsPath.get(i).get(1).toString()));

            if(i == 0){
                gMap.addPolyline(new PolylineOptions().add(Lposition, Lposition).width(5).color(Color.BLUE));
                FirstLocation = Lposition;
            }else{
                gMap.addPolyline(new PolylineOptions().add(Zlocation, Lposition).width(5).color(Color.BLUE));

                if(i == GpsPath.size()-1){
                    LastLocation = Lposition;
                    Distance = getDistance(FirstLocation, LastLocation);
                    Log.d("Showpath", String.format("%.2f", Distance)+" m");
                 }
            }

            if(Distance < 500)
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lposition, 15));
            else if(Distance < 1000)
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lposition, 14));
            else if(Distance < 10000)
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lposition, 13));


            Zlocation = Lposition;
        }
    }

    private void setCustomMarker(){
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.googlemap_custom_marker, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.custom_marker);
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private double getDistance(LatLng Flocation, LatLng Llocation) {
        double distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(Flocation.latitude);
        locationA.setLongitude(Flocation.longitude);

        Location locationB = new Location("point B");
        locationB.setLatitude(Llocation.latitude);
        locationB.setLongitude(Llocation.longitude);

        distance = locationA.distanceTo(locationB);

        Log.d("Distance", "getDistance: "+distance);
        //String double_to_string = Double.toString(Math.round(distance)/100.0);

        return distance;
    }

    /*
    private double getDistance(Location A, Location B) {

        double distance = A.distanceTo(B);
        Log.d("Distance", "getDistance: "+distance);

        return distance;
    }

     */

    /*

    private double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);

        distance = locationA.distanceTo(locationB);

        Log.d("Distance", "getDistance: "+distance);
        //String double_to_string = Double.toString(Math.round(distance)/100.0);

        return distance;
    }

     */
}
