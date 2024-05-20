package com.intervoid.parent_app_ver2;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.Edits;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.rpc.context.AttributeContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.nio.file.Paths.get;

public class Main_Activity extends AppCompatActivity
        implements OnMapReadyCallback, Serializable, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private ImageButton Child_signup;
    private ImageButton first_child;
    private ImageButton second_child;
    private ImageButton third_child;
    private ImageButton fourth_child;

    private ImageButton Sliding_button;
    private ImageButton Setting_button;
    private ImageButton History_button;

    private TextView Child_text_view;
    private TextView first_child_nametext;
    private TextView second_child_nametext;
    private TextView third_child_nametext;
    private TextView fourth_child_nametext;

    private FirebaseAuth mAuth;
    private int number_of_Children;
    SharedPreferences preferences;

    private String First_uri;
    private String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private BottomSheetBehavior bottomSheetBehavior;

    private int newState;

    private int choose_child_int = 1;
    private String Child_number;

    private long pressedTime = 0;

    Bitmap bm;
    LatLng Last_location;

    private float calMinute = 0;

    private ArrayList GetMotionData = new ArrayList<>();
    private ArrayList GetEventData = new ArrayList<>();
    private ArrayList GetLat = new ArrayList<>();
    private ArrayList GetLot = new ArrayList<>();

    private ArrayList GetPeriodicTime = new ArrayList<>();
    private ArrayList GetEventTime = new ArrayList<>();

    ArrayList LPeriodicHour = new ArrayList<>();
    ArrayList LPeriodicMinute = new ArrayList<>();

    ArrayList PmGPS = new ArrayList<>();

    public static final int G_Zero = 0;


    ////////알람//////////

    NotificationManager manager;
    NotificationCompat.Builder builder;

    String CHANNEL_ID = "channel1";
    String CHANNEL_NAME = "Channel1";


    /////////////////////

    float new_lat = 0;
    float new_lot = 0;

    /////////////////////////////////////behavior///////////////////////////////////////////////////

    private ImageButton child_image;

    private TextView child_name;
    private TextView child_subtitle_1;
    private TextView child_subtitle_2;
    public TextView child_walk_speed;
    public TextView child_run_speed;
    public TextView child_stepcount;
    public TextView child_distance;

    private TextView bottom_walk_speed_id;
    private TextView bottom_distance_id;
    private TextView bottom_average_id;
    private TextView bottom_stepcount_id;

    private ImageButton bottom_Imageupbutton;
    private ImageButton bottom_Imagedownbutton;

    private ImageButton child_call;
    private ImageButton child_message;

    private ImageButton bottom_distance_button;
    private ImageButton bottom_walk_speed_button;
    private ImageButton bottom_walk_averge_button;
    private ImageButton bottom_stepcount_button;

    private LinearLayout bottom_distance_section;
    private LinearLayout bottom_walk_speed_section;
    private LinearLayout bottom_average_walk_speed_section;
    private LinearLayout bottom_stepcount_section;

    public Spinner time_spinner;
    private TextView time_spinner_sub;

    private View view;

    private String serial_number;

    String[] items = {"Day", "Week", "Month"};

    private ArrayList<Float> gWalk_speed = new ArrayList<Float>();
    private ArrayList<Float> gWalk_avg = new ArrayList<Float>();
    private ArrayList<Float> gStep_count = new ArrayList<Float>();
    private ArrayList<Float> gDistance = new ArrayList<Float>();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private LineChart chart;

    String SpinnerItem = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Google_Map);
        mapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();
        Child_signup = (ImageButton) findViewById(R.id.Signup_Child);
        first_child = (ImageButton) findViewById(R.id.First_Child);
        second_child = (ImageButton) findViewById(R.id.Second_Child);
        third_child = (ImageButton) findViewById(R.id.Third_Child);
        fourth_child = (ImageButton) findViewById(R.id.Fourth_Child);

        Sliding_button = (ImageButton) findViewById(R.id.Sliding_button);
        Setting_button = (ImageButton) findViewById(R.id.Setting_button);
        History_button = (ImageButton) findViewById(R.id.main_History_button);

        Child_text_view = (TextView) findViewById(R.id.Set_Childern_View);
        first_child_nametext = (TextView) findViewById(R.id.first_child_name_text);
        second_child_nametext = (TextView) findViewById(R.id.second_child_name_text);
        third_child_nametext = (TextView) findViewById(R.id.third_child_name_text);
        fourth_child_nametext = (TextView) findViewById(R.id.fourth_child_name_text);

        getKeyHashBase64();

        ////////////////////////////////////////////////////////////////////////////////////////////

        child_image = (ImageButton) findViewById(R.id.bottom_Child_Image);

        child_name = (TextView) findViewById(R.id.bottom_Child_name);
        child_subtitle_1 = (TextView) findViewById(R.id.bottom_child_subtitle_1);
        child_subtitle_2 = (TextView) findViewById(R.id.bottom_child_subtitle_2);

        child_walk_speed = (TextView) findViewById(R.id.bottom_child_walk_speed);
        child_run_speed = (TextView) findViewById(R.id.bottom_child_run_speed);
        child_stepcount = (TextView) findViewById(R.id.bottom_child_stepcount);
        child_distance = (TextView) findViewById(R.id.bottom_child_distance);

        bottom_walk_speed_id = (TextView) findViewById(R.id.bottom_walk_speed_id);
        bottom_average_id = (TextView) findViewById(R.id.bottom_averge_walk_speed_id);
        bottom_stepcount_id = (TextView) findViewById(R.id.bottom_stepcount_id);
        bottom_distance_id = (TextView) findViewById(R.id.bottom_distance_id);

        child_call = (ImageButton) findViewById(R.id.bottom_call);
        child_message = (ImageButton) findViewById(R.id.bottom_message);

        time_spinner = (Spinner) findViewById(R.id.bottom_spinner);
        time_spinner_sub = (TextView) findViewById(R.id.bottom_spinner_sub);

        bottom_Imagedownbutton = (ImageButton) findViewById(R.id.bottom_layout_donwbutton);
        bottom_Imageupbutton = (ImageButton) findViewById(R.id.bottom_layout_upbutton);

        bottom_distance_button = (ImageButton) findViewById(R.id.bottom_distance_button);
        bottom_walk_speed_button = (ImageButton) findViewById(R.id.bottom_walk_speed_button);
        bottom_walk_averge_button = (ImageButton) findViewById(R.id.bottom_average_walk_speed_button);
        bottom_stepcount_button = (ImageButton) findViewById(R.id.bottom_stepcount_button);

        bottom_average_walk_speed_section = (LinearLayout) findViewById(R.id.bottom_average_walk_speed_section);
        bottom_distance_section = (LinearLayout) findViewById(R.id.bottom_distance_section);
        bottom_stepcount_section = (LinearLayout) findViewById(R.id.bottom_stepcount_section);
        bottom_walk_speed_section = (LinearLayout) findViewById(R.id.bottom_walk_speen_section);

        chart = findViewById(R.id.Line_Chart);
        LineDataSet linedataSet = new LineDataSet(dataValue(), "Data set");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(linedataSet);

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();

        ////////////////////////////////////////////////////////////////////////////////////////////

        LinearLayout bottomSheet = findViewById(R.id.bottomSheetContainer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        preferences = getSharedPreferences("child", MODE_PRIVATE);
        number_of_Children = preferences.getInt("child", 0);

        String number = Integer.toString(number_of_Children);
        String init_number = Integer.toString(choose_child_int);
        serial_number = preferences.getString(init_number+"Child_serial", "NO_Serial");
        Child_number = preferences.getString(number+"Child_Number", "No_Number");

        Check_Permission();

        Log.d("child", "onCreate: "+choose_child_int);

        if(number_of_Children > 4 || number_of_Children == 0){
            first_child.setVisibility(View.GONE);
            second_child.setVisibility(View.GONE);
            third_child.setVisibility(View.GONE);
            fourth_child.setVisibility(View.GONE);

            Child_text_view.setVisibility(View.VISIBLE);
            Child_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main_Activity.this, Getchild_Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if(number_of_Children == 1){

            child_circle_image(1);

            Default_ChildImage();

            Child_text_view.setVisibility(View.GONE);
            Child_signup.setVisibility(View.GONE);
            second_child.setVisibility(View.GONE);
            third_child.setVisibility(View.GONE);
            fourth_child.setVisibility(View.GONE);

        } else if(number_of_Children == 2){

            child_circle_image(1);
            child_circle_image(2);

            Default_ChildImage();

            Child_text_view.setVisibility(View.GONE);
            Child_signup.setVisibility(View.GONE);
            third_child.setVisibility(View.GONE);
            fourth_child.setVisibility(View.GONE);
        } else if(number_of_Children == 3){

            child_circle_image(1);
            child_circle_image(2);
            child_circle_image(3);

            Default_ChildImage();

            Child_text_view.setVisibility(View.GONE);
            Child_signup.setVisibility(View.GONE);
            fourth_child.setVisibility(View.GONE);
        } else if(number_of_Children == 4){

            child_circle_image(1);
            child_circle_image(2);
            child_circle_image(3);
            child_circle_image(4);

            Default_ChildImage();

            Child_text_view.setVisibility(View.GONE);
            Child_signup.setVisibility(View.GONE);
        }

        bottom_Imageupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        bottom_Imagedownbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        Sliding_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        first_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_child(1);
                bm = getBitmapFromCache("1child.png");
                choose_childPosition(1,bm);
            }
        });

        second_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_child(2);
                bm = getBitmapFromCache("2child.png");
                choose_childPosition(2,bm);
            }
        });

        third_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_child(3);
                bm = getBitmapFromCache("3child.png");
                choose_childPosition(3,bm);
            }
        });

        fourth_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_child(4);
                bm = getBitmapFromCache("4child.png");
                choose_childPosition(4,bm);
            }
        });

        Setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(Main_Activity.this, Setting_Activity.class);
                settingIntent.putExtra("number_of_Children",number_of_Children);
                startActivity(settingIntent);
            }
        });

        History_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(Main_Activity.this, History_Activity.class);
                historyIntent.putExtra("PeriodicList", GetMotionData);
                historyIntent.putExtra("gLAT", GetLat);
                historyIntent.putExtra("gLOT", GetLot);
                historyIntent.putExtra("gPeriodicTime", GetPeriodicTime);
                historyIntent.putExtra("gEventTime", GetEventTime);
                historyIntent.putExtra("gHour", LPeriodicHour);
                historyIntent.putExtra("gMinute", LPeriodicMinute);

                String name = preferences.getString(choose_child_int+"Child_Name","No_name");
                historyIntent.putExtra("gName", name);

                startActivity(historyIntent);
            }
        });

        bottom_walk_speed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+"Walk_Speed");
            }
        });

        ///////////////////////////////////////////

        final NotificationManager notificationManager =
                (NotificationManager)Main_Activity.this.getSystemService(Main_Activity.this.NOTIFICATION_SERVICE);

        final Intent intent = new Intent(Main_Activity.this.getApplicationContext(), Main_Activity.class);

        //////////////////////////////////////////

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                Main_Activity.this, android.R.layout.simple_spinner_dropdown_item, items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        time_spinner.setAdapter(adapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);

                DateFormat YeardateFormat = new SimpleDateFormat("yyyy");
                String StringYear = YeardateFormat.format(mDate);

                DateFormat MonthdateFormat = new SimpleDateFormat("MM");
                String StringMonth = MonthdateFormat.format(mDate);

                DateFormat DaydateFormat = new SimpleDateFormat("dd");
                String StringDay = DaydateFormat.format(mDate);

                DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
                String StringTime = TimedateFormat.format(mDate);

                if(items[position] == "Day") {
                    // 하루일 경우 ~
                    SpinnerItem = (String)time_spinner.getSelectedItem();
                    DatabaseReference mDay = mDatabase.child("users").child(serial_number);
                    mDay.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try{
                                users group = snapshot.getValue(users.class);
                                Map data = group.getData();

                                long now = System.currentTimeMillis();
                                Date mDate = new Date(now);

                                DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
                                String StringTime = TimedateFormat.format(mDate);

                                Date currentTime = null;

                                try{
                                    currentTime = TimedateFormat.parse(StringTime);
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }

                                gWalk_avg.clear();
                                gDistance.clear();
                                gStep_count.clear();
                                gWalk_speed.clear();

                                Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                                Map<String, Object> YearData = (Map<String, Object>) PeriodicData.get(StringYear);
                                Map<String, Object> MonthData = (Map<String, Object>) YearData.get(StringMonth);
                                Map<String, Object> DayData = (Map<String, Object>) MonthData.get(StringDay);
                                Map<String, Object> TimeData = null;
                                Map<String, Object> Max_TimeData = null;

                                float mSpeed = 0;
                                float mStep_count = 0;
                                float mWalk_avg = 0;
                                float mDistance = 0;

                                int MaxHour = 0;
                                int LastMinute = 0;

                                //DayData = (Map<String, Object>) MonthData.get(Integer.toString(25));

                                for (int LocalHour = 0; LocalHour < 23; LocalHour++) {
                                    for (int LocalMinute = 0; LocalMinute < 59; LocalMinute++) {
                                        for(int LocalSec = 0; LocalSec < 59; LocalSec++) {
                                            TimeData = (Map<String, Object>) DayData.get(Integer.toString(LocalHour) + ":" + String.format("%02d", LocalMinute) + ":"+String.format("%02d", LocalSec));
                                            if (TimeData != null) {
                                                Log.d("DTimeDataDay", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec) + TimeData);

                                                if(TimeData.get("Speed") == null){
                                                    mSpeed = G_Zero;
                                                    mStep_count = G_Zero;

                                                    mDistance = G_Zero;
                                                    mWalk_avg = G_Zero;

                                                    if (MaxHour < LocalHour)
                                                        MaxHour = LocalHour;

                                                    gWalk_speed.add((float)G_Zero);
                                                    gStep_count.add((float)G_Zero);
                                                    gDistance.add((float)G_Zero);
                                                    mDistance = (float)G_Zero;
                                                    mWalk_avg = (float)G_Zero;

                                                    gWalk_avg.add((float)G_Zero);

                                                    LastMinute = LocalMinute;

                                                }else{
                                                    mSpeed = Float.parseFloat(TimeData.get("Speed").toString());
                                                    mStep_count = Float.parseFloat(TimeData.get("Step_count").toString());

                                                    mDistance = mStep_count * (float) 0.4;
                                                    mWalk_avg = mSpeed / (float) 6;

                                                    if (MaxHour < LocalHour)
                                                        MaxHour = LocalHour;

                                                    gWalk_speed.add(mSpeed);
                                                    gStep_count.add(mStep_count);
                                                    gDistance.add(mStep_count * (float) 0.4);
                                                    mDistance = mStep_count * (float) 0.4;
                                                    mWalk_avg = mSpeed / (float) 6;

                                                    gWalk_avg.add(mWalk_avg);

                                                    LastMinute = LocalMinute;
                                                }
                                            }else
                                                continue;
                                        }
                                    }
                                }
                                String last_time = MaxHour + ":" + LastMinute + ":00";
                                Log.d("last_time", last_time);
                                Date lastData = null;

                                SimpleDateFormat lastTime = new SimpleDateFormat("HH:mm:ss");
                                try {
                                    lastData = lastTime.parse(last_time);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                float calDate = Math.abs(currentTime.getTime() - lastData.getTime());
                                calMinute = calDate / 60000;
                                Log.d("calDate", String.format("%.0f", calMinute) + " 분전");
                                child_subtitle_1.setText(String.format("%.0f", calMinute) + " 분전");
                                child_subtitle_2.setText(last_time);

                                child_walk_speed.setText("    "+String.format("%.2f", mSpeed)+" m/s");
                                child_stepcount.setText("    "+String.format("%.2f", mStep_count)+ "보");
                                child_run_speed.setText("    "+String.format("%.2f", mWalk_avg)+" m/m");
                                child_distance.setText("    "+String.format("%.2f", mDistance)+" m");
                                Log.d("mDistance1", String.format("%.2f", mDistance)+" m");

                            }catch (NullPointerException e){
                                Log.d("Selectedspinner", "nullpointerror: "+e);
                            } catch (Exception e){
                                Log.d("Selectedspinner", "exception: "+e);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                else if(items[position] == "Week"){
                    DatabaseReference mWeek = mDatabase.child("users").child(serial_number);
                    mWeek.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try{
                                users group = snapshot.getValue(users.class);
                                Map data = group.getData();

                                gWalk_avg.clear();
                                gDistance.clear();
                                gStep_count.clear();
                                gWalk_speed.clear();

                                Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                                Map<String, Object> YearData = (Map<String, Object>) PeriodicData.get(StringYear);
                                Map<String, Object> MonthData = (Map<String, Object>) YearData.get(StringMonth);
                                Map<String, Object> DayData = (Map<String, Object>) MonthData.get(StringDay);
                                Map<String, Object> TimeData = null;

                                float mSpeed = 0;
                                float mStep_count = 0;
                                float mWalk_avg = 0;
                                float mDistance = 0;

                                int MaxHour = 0;
                                int MaxMinute = 0;

                                int mToday = Integer.parseInt(StringDay);

                                int WeekCount= 0;

                                float SumSpeed = 0;
                                float SumStep_count = 0;
                                float SumWalk_avg = 0;
                                float SumDistance = 0;

                                for(int LocalWeek = mToday; LocalWeek >= (mToday-7) ; LocalWeek--) {
                                    DayData = (Map<String, Object>) MonthData.get(Integer.toString(LocalWeek));
                                    if (DayData != null) {
                                        for (int LocalHour = 0; LocalHour < 23; LocalHour++) {
                                            if (MaxHour < LocalHour)
                                                MaxHour = LocalHour;
                                            for (int LocalMinute = 0; LocalMinute < 59; LocalMinute++) {
                                                for(int LocalSec = 0; LocalSec < 59; LocalSec++) {
                                                    TimeData = (Map<String, Object>) DayData.get(Integer.toString(LocalHour) + ":" + String.format("%02d", LocalMinute) + ":"+String.format("%02d", LocalSec));
                                                    if (TimeData != null) {

                                                        if(TimeData.get("Speed") == null) {
                                                            Log.d("WTimeDataDay", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":" + Integer.toString(LocalSec) + TimeData);

                                                            mSpeed = G_Zero;
                                                            mStep_count = G_Zero;

                                                            SumSpeed += mSpeed;
                                                            SumStep_count += mStep_count;

                                                            mDistance = G_Zero;
                                                            mWalk_avg = G_Zero;

                                                            gWalk_speed.add((float)G_Zero);
                                                            gStep_count.add((float)G_Zero);
                                                            gDistance.add((float)G_Zero);
                                                            gWalk_avg.add((float)G_Zero);

                                                            mDistance = (float)G_Zero;
                                                            mWalk_avg = (float)G_Zero;

                                                            WeekCount++;
                                                        }else {
                                                            Log.d("WTimeDataDay", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":" + Integer.toString(LocalSec) + TimeData);

                                                            mSpeed = Float.parseFloat(TimeData.get("Speed").toString());
                                                            mStep_count = Float.parseFloat(TimeData.get("Step_count").toString());

                                                            SumSpeed += mSpeed;
                                                            SumStep_count += mStep_count;

                                                            mDistance = mStep_count * (float) 0.4;
                                                            mWalk_avg = mSpeed / (float) 6;

                                                            gWalk_speed.add(mSpeed);
                                                            gStep_count.add(mStep_count);
                                                            gDistance.add(mStep_count * (float) 0.4);
                                                            gWalk_avg.add(mWalk_avg);

                                                            mDistance = mStep_count * (float) 0.4;
                                                            mWalk_avg = mSpeed / (float) 6;

                                                            WeekCount++;
                                                        }
                                                    } else
                                                        continue;
                                                }
                                            }
                                        }

                                    }else
                                        continue;
                                }

                                SumSpeed /= WeekCount;

                                for(int i = 0; i<gWalk_avg.size(); i++){
                                    SumWalk_avg += gWalk_avg.get(i);
                                }
                                SumWalk_avg /= gWalk_avg.size();

                                for(int j = 0; j<gDistance.size(); j++){
                                    SumDistance += gDistance.get(j);
                                }
                                SumDistance /= gDistance.size();

                                child_walk_speed.setText("    "+String.format("%.2f", SumSpeed)+" m/s");
                                child_stepcount.setText("    "+String.format("%.2f", SumStep_count)+ "보");
                                child_run_speed.setText("    "+String.format("%.2f", SumWalk_avg)+" m/m");
                                child_distance.setText("    "+String.format("%.2f", SumDistance)+" m");

                            }catch (NullPointerException e){
                                Log.d("Selectedspinner", "nullpointerror: "+e);
                            } catch (Exception e){
                                Log.d("Selectedspinner", "exception: "+e);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                else {
                    // 나머지일 경우(한달)
                    DatabaseReference mMonth = mDatabase.child("users").child(serial_number);
                    mMonth.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try{
                                users group = snapshot.getValue(users.class);
                                Map data = group.getData();

                                gWalk_avg.clear();
                                gDistance.clear();
                                gStep_count.clear();
                                gWalk_speed.clear();

                                Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                                Map<String, Object> YearData = (Map<String, Object>) PeriodicData.get(StringYear);
                                Map<String, Object> MonthData = (Map<String, Object>) YearData.get(StringMonth);
                                Map<String, Object> DayData = null;
                                Map<String, Object> TimeData = null;

                                float mSpeed = 0;
                                float mStep_count = 0;
                                float mWalk_avg = 0;
                                float mDistance = 0;

                                int MaxHour = 0;
                                int MaxMinute = 0;

                                int DayCount= 0;

                                float SumSpeed = 0;
                                float SumStep_count = 0;
                                float SumWalk_avg = 0;
                                float SumDistance = 0;


                                for(int LocalDay = 1; LocalDay < 31; LocalDay++) {
                                    DayData = (Map<String, Object>) MonthData.get(Integer.toString(LocalDay));
                                    if(DayData != null) {
                                        for (int LocalHour = 0; LocalHour < 23; LocalHour++) {
                                            if (MaxHour < LocalHour)
                                                MaxHour = LocalHour;
                                            for (int LocalMinute = 0; LocalMinute < 59; LocalMinute++) {
                                                for(int LocalSec = 0; LocalSec < 59; LocalSec++) {
                                                    TimeData = (Map<String, Object>) DayData.get(Integer.toString(LocalHour) + ":" + String.format("%02d", LocalMinute) + ":"+String.format("%02d", LocalSec));
                                                    if (TimeData != null) {

                                                        if(TimeData.get("Speed") == null) {
                                                            mSpeed = G_Zero;
                                                            mStep_count = G_Zero;

                                                            SumSpeed += mSpeed;
                                                            SumStep_count += mStep_count;

                                                            mWalk_avg = G_Zero;
                                                            mDistance = G_Zero;

                                                            gWalk_speed.add((float)G_Zero);
                                                            gStep_count.add((float)G_Zero);
                                                            gDistance.add((float)G_Zero);
                                                            gWalk_avg.add((float)G_Zero);

                                                            DayCount++;
                                                        }else{
                                                            Log.d("MTimeDataDay", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":" + Integer.toString(LocalSec) + TimeData);

                                                            mSpeed = Float.parseFloat(TimeData.get("Speed").toString());
                                                            mStep_count = Float.parseFloat(TimeData.get("Step_count").toString());

                                                            SumSpeed += mSpeed;
                                                            SumStep_count += mStep_count;

                                                            mWalk_avg = mSpeed / (float) 6;
                                                            mDistance = mStep_count * (float) 0.4;

                                                            gWalk_speed.add(mSpeed);
                                                            gStep_count.add(mStep_count);
                                                            gDistance.add(mStep_count * (float) 0.4);
                                                            gWalk_avg.add(mWalk_avg);

                                                            DayCount++;
                                                        }
                                                    } else
                                                        continue;
                                                }
                                            }
                                        }
                                    } else
                                        continue;
                                }

                                SumSpeed /= DayCount;

                                for(int i = 0; i<gWalk_avg.size(); i++){
                                    SumWalk_avg += gWalk_avg.get(i);
                                }
                                SumWalk_avg /= gWalk_avg.size();

                                for(int j = 0; j<gDistance.size(); j++){
                                    SumDistance += gDistance.get(j);
                                }
                                SumDistance /= gDistance.size();

                                child_walk_speed.setText("    "+String.format("%.2f", SumSpeed)+" m/s");
                                child_stepcount.setText("    "+String.format("%.2f", SumStep_count)+ "보");
                                child_run_speed.setText("    "+String.format("%.2f", SumWalk_avg)+" m/m");
                                child_distance.setText("    "+String.format("%.2f", SumDistance)+" m");
                                Log.d("mDistance3", String.format("%.2f", mDistance)+" m");

                                Log.d("mSpeed", String.format("%.2f", mSpeed));

                            }catch (NullPointerException e){
                                Log.d("Selectedspinner", "nullpointerror: "+e);
                            } catch (Exception e){
                                Log.d("Selectedspinner", "exception: "+e);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ;
            }


        });

        child_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent childIntent = new Intent(Main_Activity.this, Detail_Activity.class);
                childIntent.putExtra("choose_child",choose_child_int);
                startActivity(childIntent);
            }
        });

        child_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = "tel:"+Child_number;
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
                startActivity(callIntent);
            }
        });

        child_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = "sms:"+Child_number;
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(sms));
                startActivity(messageIntent);
            }
        });

        bottom_walk_speed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked_imagebutton(1);
                make_clicked_graph(gWalk_speed);
            }
        });

        bottom_walk_averge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Step_count_mode = 3;
                Clicked_imagebutton(2);
                make_clicked_graph(gWalk_avg);
            }
        });

        bottom_stepcount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked_imagebutton(3);
                make_clicked_graph(gStep_count);
            }
        });

        bottom_distance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked_imagebutton(4);
                make_clicked_graph(gDistance);
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        Defalut_Location();

        String mmSerial_number = null;
        String String_choose_child;
        Bitmap mbitmap = null;

        switch (choose_child_int){
            case 1:
                String_choose_child = Integer.toString(choose_child_int);
                mmSerial_number = preferences.getString(String_choose_child+"Child_serial", "No_serial");
                mbitmap = getBitmapFromCache(String_choose_child+"child.png");
                break;

            case 2:
                String_choose_child = Integer.toString(choose_child_int);
                mmSerial_number = preferences.getString(String_choose_child+"Child_serial", "No_serial");
                mbitmap = getBitmapFromCache(String_choose_child+"child.png");
                break;

            case 3:
                String_choose_child = Integer.toString(choose_child_int);
                mmSerial_number = preferences.getString(String_choose_child+"Child_serial", "No_serial");
                mbitmap = getBitmapFromCache(String_choose_child+"child.png");
                break;

            case 4:
                String_choose_child = Integer.toString(choose_child_int);
                mmSerial_number = preferences.getString(String_choose_child+"Child_serial", "No_serial");
                mbitmap = getBitmapFromCache(String_choose_child+"child.png");
                break;

            default:
                Log.d("onmapready", "onMapReady: error");
                break;
        }

        mMap.setOnMarkerClickListener(this);
        Get_Perodic_value(mmSerial_number, mbitmap);
    }

    public void getKeyHashBase64() {
        PackageInfo packageInfo = null;
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        if(packageInfo == null)
            Log.e("KeyHash", "KeyHash: null");

        for(Signature signature : packageInfo.signatures){
            try{
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e){
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    private void Get_Perodic_value(String mSerial_number, Bitmap bitmap){
        DatabaseReference mDay = mDatabase.child("users").child(mSerial_number);
        mDay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    users group = snapshot.getValue(users.class);
                    Map data = group.getData();

                    mMap.clear();

                    String string_number = Integer.toString(choose_child_int);
                    String child_name = preferences.getString(string_number+"Child_Name","No_name");

                    GetMotionData.clear();
                    GetPeriodicTime.clear();

                    LPeriodicHour.clear();
                    LPeriodicMinute.clear();

                    GetLot.clear();
                    GetLat.clear();

                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);

                    DateFormat YeardateFormat = new SimpleDateFormat("yyyy");
                    String StringYear = YeardateFormat.format(mDate);

                    DateFormat MonthdateFormat = new SimpleDateFormat("MM");
                    String StringMonth = MonthdateFormat.format(mDate);
                    Log.d("MONTH", StringMonth);

                    DateFormat DaydateFormat = new SimpleDateFormat("dd");
                    String StringDay = DaydateFormat.format(mDate);
                    Log.d("Day", StringDay);

                    DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
                    String StringTime = TimedateFormat.format(mDate);
                    Log.d("Time", StringTime);

                    Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                    Map<String, Object> PYearData = (Map<String, Object>) PeriodicData.get(StringYear);
                    Map<String, Object> PMonthData = (Map<String, Object>) PYearData.get(StringMonth);
                    Map<String, Object> PDayData = (Map<String, Object>) PMonthData.get(StringDay);
                    Map<String, ArrayList> PTimeData = null;

                    Map<String, Object> EventData = (Map<String, Object>) data.get("Event");
                    Map<String, Object> EYearData = (Map<String, Object>) EventData.get(StringYear);
                    Map<String, Object> EMonthData = (Map<String, Object>) EYearData.get(StringMonth);
                    Map<String, Object> EDayData = (Map<String, Object>) EMonthData.get(StringDay);
                    Map<String, ArrayList> ETimeData = null;

                    ArrayList PGPSData = new ArrayList<>();
                    ArrayList EGPSData = new ArrayList<>();

                    ArrayList<ArrayList> PmGPS = new ArrayList<ArrayList>();
                    ArrayList<ArrayList> EmGPS = new ArrayList<ArrayList>();

                    ArrayList<ArrayList> FirstGPS = new ArrayList<ArrayList>();
                    ArrayList<ArrayList> LastGPS = new ArrayList<ArrayList>();

                    new_lat = 0;
                    new_lot = 0;

                    int MaxHour = 0;
                    int LastMinute = 0;

                    int WalkAlarm = preferences.getInt(string_number+"Child_WalkAlarm", 1);
                    int RunAlarm = preferences.getInt(string_number+"Child_RunAlarm", 1);

                    for (int LocalHour = 0; LocalHour < 23; LocalHour++) {
                        LPeriodicHour.add(LocalHour);
                        for (int LocalMinute = 0; LocalMinute < 59; LocalMinute++) {
                            for(int LocalSec = 0; LocalSec < 59; LocalSec++) {

                                PTimeData = (Map<String, ArrayList>) PDayData.get(Integer.toString(LocalHour) + ":" + String.format("%02d", LocalMinute) + ":"+String.format("%02d", LocalSec));
                                //Log.d("PTimeData", PTimeData.toString());

                                if (PTimeData != null) {
                                    Log.d("TimeData_P", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec) + PTimeData);
                                    PGPSData.add((PTimeData.get("GPS")));
                                    Log.d("GPSData", String.valueOf(PGPSData));

                                    PmGPS.add(PTimeData.get("GPS"));
                                    Log.d("PmGPS", String.valueOf(PmGPS));
                                    //EmGPS.add(ETimeData.get("GPS"));

                                    FirstGPS.add((PTimeData.get("GPS")));

                                    if (CalDistance(FirstGPS, LastGPS) < 5.0) {
                                        LPeriodicMinute.add(LocalMinute);
                                        GetMotionData.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+ Integer.toString(LocalSec) + child_name + " (이)가 멈춤.");
                                        GetPeriodicTime.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec));
                                    } else {
                                        LPeriodicMinute.add(LocalMinute);
                                        GetMotionData.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":" + Integer.toString(LocalSec) + child_name + " (이)가 이동중.");
                                        GetPeriodicTime.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec));
                                    }


                                    //LPeriodicMinute.add(LocalMinute);
                                    //GetMotionData.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":00 "+child_name+" (이)가 이동중.");
                                    //GetPeriodicTime.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":00");

                                /*
                                if(WalkAlarm == 1){
                                    Send_KakaoTalk(LocalHour, LocalMinute, child_name);
                                }

                                 */

                                    if (MaxHour < LocalHour)
                                        MaxHour = LocalHour;

                                    LastGPS.add(FirstGPS);

                                    LastMinute = LocalMinute;
                                }

                                ETimeData = (Map<String, ArrayList>) EDayData.get(Integer.toString(LocalHour) + ":" + String.format("%02d", LocalMinute) + ":"+String.format("%02d",LocalSec));

                                if(ETimeData != null) {
                                    Log.d("TimeData_P", Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":" + Integer.toString(LocalSec) + PTimeData);
                                    PGPSData.add((ETimeData.get("GPS")));
                                    Log.d("GPSData", String.valueOf(PGPSData));

                                    PmGPS.add(ETimeData.get("GPS"));
                                    Log.d("EmGPS", String.valueOf(PmGPS));
                                    LPeriodicMinute.add(LocalMinute);
                                    GetMotionData.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec)+child_name+" (이)가 뛰는중.");
                                    GetPeriodicTime.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":"+Integer.toString(LocalSec));


                                /*
                                if(RunAlarm == 1){
                                    Send_KakaoTalk(LocalHour, LocalMinute, child_name);
                                }

                                 */

                                    if(MaxHour < LocalHour)
                                        MaxHour = LocalHour;

                                    LastMinute = LocalMinute;
                                }else
                                    continue;
                            }
                        }
                    }

                    Log.d("EndFOR", "onDataChange: ");

                    /////////PERIODIC_DAPA////////////////////////////////////////////////////

                    for(int GetGPS = 0; GetGPS < PmGPS.size(); GetGPS++){
                        Log.d("Get_PmGPS", String.valueOf(PmGPS.get(GetGPS)));
                        new_lat = Float.parseFloat(PmGPS.get(GetGPS).get(0).toString());
                        Log.d("newLat", Float.toString(new_lat));
                        GetLat.add(PmGPS.get(GetGPS).get(0));

                        Log.d("Get_PmGPS", String.valueOf(PmGPS.get(GetGPS)));
                        new_lot = Float.parseFloat(PmGPS.get(GetGPS).get(1).toString());
                        Log.d("newLot", Float.toString(new_lot));
                        GetLot.add(PmGPS.get(GetGPS).get(1));
                    }

                    /////////EVENT_DAPA////////////////////////////////////////////////////


                    /*
                    for(int j = 0; j<EmGPS.size(); j++){
                        Log.d("EmGPS", String.valueOf(EmGPS.get(j)));
                        new_lat = Float.parseFloat(EmGPS.get(j).get(0).toString());
                        Log.d("EmGPS", Float.toString(new_lat));
                        GetLat.add(EmGPS.get(j).get(0));

                        new_lot = Float.parseFloat(EmGPS.get(j).get(1).toString());
                        Log.d("EmGPS", Float.toString(new_lot));
                        GetLot.add(EmGPS.get(j).get(1));
                    }


                     */
                    //kaKaoAlram(string_number);

                    float LcalMinute = TitleTime(MaxHour, LastMinute);
                    LatLng Location = new LatLng(new_lat, new_lot);

                    Marker Walkmarker = mMap.addMarker(new MarkerOptions()
                            .position(Location)
                            .title(String.format("%.0f", LcalMinute) +" 분전")
                            .snippet("클릭시 경로 확인")
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

                    Walkmarker.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 18));
                    Last_location = Location;

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent ShowPath = new Intent(Main_Activity.this, GoogleMap_ShowPath.class);
                            ShowPath.putExtra("GPS_Value", PmGPS);
                            startActivity(ShowPath);
                        }
                    });

                }catch (NullPointerException e){
                    Log.d("mPeriodic", "nullpointerror: "+e);
                } catch (Exception e){
                    Log.d("mPeriodic", "exception: "+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void KaKaoAlram(String StringNumber){

        int LocalMinute = 0;
        int LocalHour = 0;

        String child_name = preferences.getString(StringNumber+"Child_Name","No_name");

        GetMotionData.clear();
        GetPeriodicTime.clear();

        for(int mHour = 0; mHour < LPeriodicHour.size(); mHour++){
            LocalHour = (Integer) LPeriodicHour.get(mHour);

            for(int mMinute = 0; mMinute < LPeriodicMinute.size(); mMinute++){
                LocalMinute = (Integer) LPeriodicMinute.get(mMinute);
                GetMotionData.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":00 "+child_name+" (이)가 이동중.");
                GetPeriodicTime.add(Integer.toString(LocalHour) + ":" + Integer.toString(LocalMinute) + ":00");
            }
        }

        int WalkAlarm = preferences.getInt(StringNumber+"Child_WalkAlarm", 1);

        if(WalkAlarm == 1){
            Send_KakaoTalk(LocalHour, LocalMinute, child_name);
        }
    }

    private float TitleTime(int MaxHour, int MaxMinute){
        Date currentTime = null;
        Date lastData = null;
        float LcalMinute = 0;

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        SimpleDateFormat lastTime = new SimpleDateFormat("HH:mm:ss");
        String last_time = MaxHour+":"+MaxMinute+":00";

        DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
        String StringTime = TimedateFormat.format(mDate);

        try{
            lastData = lastTime.parse(last_time);
            currentTime = TimedateFormat.parse(StringTime);
        }catch (ParseException e){
            e.printStackTrace();
        }

        float calDate = Math.abs(currentTime.getTime()-lastData.getTime());
        LcalMinute = calDate / 60000;
        Log.d("calDate", String.format("%.0f", LcalMinute) +" 분전");

        return LcalMinute;
    }

    private void Send_KakaoTalk(int mHour, int mMinute, String mName){

        Intent sharingIntent = new Intent(Intent.ACTION_SENDTO);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Integer.toString(mHour) + ":" + Integer.toString(mMinute) + ":00 "+mName+" (이)가 이동을 시작했습니다.");
        sharingIntent.setPackage("com.kakao.talk");
        startActivity(sharingIntent);
    }

    private double CalDistance(ArrayList<ArrayList> BeforeVal, ArrayList<ArrayList> AfterVal){

        double beforeLat = 0;
        double beforeLot = 0;

        double afterLat = 0;
        double afterLot = 0;

        for(int i = 0; i<BeforeVal.size(); i++) {
            Log.d("PmGPS", String.valueOf(BeforeVal.get(i)));
            beforeLat = Float.parseFloat(BeforeVal.get(i).get(0).toString());
            beforeLot = Float.parseFloat(BeforeVal.get(i).get(1).toString());
        }

        for(int j = 0; j<AfterVal.size(); j++) {
            Log.d("PmGPS", String.valueOf(BeforeVal.get(j)));
            afterLat = Float.parseFloat(BeforeVal.get(j).get(0).toString());
            afterLot = Float.parseFloat(BeforeVal.get(j).get(1).toString());
        }

        Location locationA = new Location("point A");
        locationA.setLatitude(afterLat);
        locationA.setLongitude(afterLot);

        Location locationB = new Location("point B");
        locationB.setLatitude(beforeLat);
        locationB.setLongitude(beforeLot);

        double distance = locationA.distanceTo(locationB);

        Log.d("Distance", "getDistance: "+distance);
        //String double_to_string = Double.toString(Math.round(distance)/100.0);

        return distance;
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

    public double getDistance(double lat1, double lng1, double lat2, double lng2) {
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

    private void choose_childPosition(int choose_child_number, Bitmap bitmap){

        String string_number = Integer.toString(choose_child_number);
        String mSerial_number = preferences.getString(string_number+"Child_serial", "No_serial");

        Get_Perodic_value(mSerial_number, bitmap);
    }

    private Bitmap getBitmapFromCache(String key) {
        String found = null;
        Bitmap bitmap = null;

        File file = new File(getApplicationContext().getCacheDir().toString());
        File[] files = file.listFiles();

        for (File tempFile : files) {
            if(tempFile.getName().contains(key)) {
                found = (tempFile.getName());
                String path = getApplicationContext().getCacheDir().toString() + "/" + found;
                bitmap = BitmapFactory.decodeFile(path);
            }
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {

        if(System.currentTimeMillis() > pressedTime + 2000){
            pressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"종료",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            for(int i = 0; i< grantResults.length; i++) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    ;
                }else{
                    Toast.makeText(Main_Activity.this, "앱 권한을 설정하세요.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        }
    }

    public void Check_Permission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED)
                requestPermissions(permission_list, 0);
        }
    }

    public Uri get_imageUri(String Child_uri) {
        First_uri = preferences.getString(Child_uri,"0");
        Uri uri = Uri.parse(First_uri);
        return uri;
    }

    private void child_circle_image(int number) {
        String numbers = Integer.toString(number);
        Uri F_uri = get_imageUri(numbers+"uri");
        make_circle_child_image(F_uri,number);

        String kid_name = preferences.getString(numbers+"Child_Name", "No_Name");

        switch (number){
            case 1:
                first_child_nametext.setText(kid_name);
                break;

            case 2:
                second_child_nametext.setText(kid_name);
                break;

            case 3:
                third_child_nametext.setText(kid_name);
                break;

            case 4:
                fourth_child_nametext.setText(kid_name);
                break;

            default:
                Log.d("Switch", "switch_error");
                break;
        }
    }

    public void Default_ChildImage(){
        String Kid_name = preferences.getString("1Child_Name", "kid");
        Uri Child_uri = get_imageUri("1uri");
        make_child_image(Child_uri);
        child_name.setText(Kid_name);
    }

    private ArrayList<Entry> dataValue() {

        ArrayList<Entry> datavals = new ArrayList<Entry>();
        datavals.add(new Entry(1,0));
        return datavals;
    }

    private void make_child_image(Uri uri) {

        Glide.with(this)
                .load(uri)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(child_image);
    }

    private void make_circle_child_image(Uri uri, int number) {

        switch (number){
            case 1:
                Glide.with(this)
                        .load(uri)
                        .circleCrop()
                        .into(first_child);
                break;

            case 2:
                Glide.with(this)
                        .load(uri)
                        .circleCrop()
                        .into(second_child);
                break;

            case 3:
                Glide.with(this)
                        .load(uri)
                        .circleCrop()
                        .into(third_child);
                break;

            case 4:
                Glide.with(this)
                        .load(uri)
                        .circleCrop()
                        .into(fourth_child);
                break;

            default:
                Log.d("Glide", "make_circle_child_image_switch_error");
        }
    }

    private void choose_child(int number){
        String numbers = Integer.toString(number);
        String kid_name = preferences.getString(numbers+"Child_Name", numbers+"_Noname");
        serial_number = preferences.getString(numbers+"Child_serial", numbers+"_No_serial");
        Child_number = preferences.getString(number+"Child_Number", "No_Number");
        choose_child_int = number;

        Uri child_uri = get_imageUri(numbers+"uri");
        make_child_image(child_uri);
        child_name.setText(kid_name);
    }

    private void Clicked_imagebutton(int Clicked_Button){

        switch (Clicked_Button){
            case 1:
                bottom_walk_speed_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_clicked_round));
                bottom_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_click_image_button));
                bottom_walk_speed_id.setTextColor(Color.WHITE);
                child_walk_speed.setTextColor(Color.WHITE);

                bottom_distance_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_distance_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_distance_id.setTextColor(Color.GRAY);
                child_distance.setTextColor(Color.GRAY);

                bottom_stepcount_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_stepcount_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_stepcount_id.setTextColor(Color.GRAY);
                child_stepcount.setTextColor(Color.GRAY);

                bottom_walk_averge_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_average_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_average_id.setTextColor(Color.GRAY);
                child_run_speed.setTextColor(Color.GRAY);

                break;

                case 2:
                    bottom_walk_averge_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_clicked_round));
                    bottom_average_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_click_image_button));
                    bottom_average_id.setTextColor(Color.WHITE);
                    child_run_speed.setTextColor(Color.WHITE);

                    bottom_distance_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                    bottom_distance_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                    bottom_distance_id.setTextColor(Color.GRAY);
                    child_distance.setTextColor(Color.GRAY);

                    bottom_walk_speed_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                    bottom_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                    bottom_walk_speed_id.setTextColor(Color.GRAY);
                    child_walk_speed.setTextColor(Color.GRAY);

                    bottom_stepcount_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                    bottom_stepcount_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                    bottom_stepcount_id.setTextColor(Color.GRAY);
                    child_stepcount.setTextColor(Color.GRAY);

                    break;

            case 3:
                bottom_stepcount_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_clicked_round));
                bottom_stepcount_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_click_image_button));
                bottom_stepcount_id.setTextColor(Color.WHITE);
                child_stepcount.setTextColor(Color.WHITE);

                bottom_distance_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_distance_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_distance_id.setTextColor(Color.GRAY);
                child_distance.setTextColor(Color.GRAY);

                bottom_walk_speed_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_walk_speed_id.setTextColor(Color.GRAY);
                child_walk_speed.setTextColor(Color.GRAY);

                bottom_walk_averge_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_average_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_average_id.setTextColor(Color.GRAY);
                child_run_speed.setTextColor(Color.GRAY);

                break;

            case 4:
                bottom_distance_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_clicked_round));
                bottom_distance_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_click_image_button));
                bottom_distance_id.setTextColor(Color.WHITE);
                child_distance.setTextColor(Color.WHITE);

                bottom_walk_speed_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_walk_speed_id.setTextColor(Color.GRAY);
                child_walk_speed.setTextColor(Color.GRAY);

                bottom_walk_averge_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_average_walk_speed_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_average_id.setTextColor(Color.GRAY);
                child_run_speed.setTextColor(Color.GRAY);

                bottom_stepcount_button.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.button_round));
                bottom_stepcount_section.setBackground(ContextCompat.getDrawable(Main_Activity.this, R.drawable.bottom_noclick_image_button));
                bottom_stepcount_id.setTextColor(Color.GRAY);
                child_stepcount.setTextColor(Color.GRAY);

                break;

            default:
                Log.d("SWITCH", "Clicked_imagebutton_switch_error");
        }
    }


    private void make_clicked_graph(ArrayList<Float> Timearray){

        ArrayList<Entry> dataval = new ArrayList<Entry>();

        for(int i = 0; i<Timearray.size(); i++){
            dataval.add(new Entry(i, Timearray.get(i)));
        }

        LineDataSet linedataSet = new LineDataSet(dataval,"data set");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(linedataSet);

        LineData data = new LineData(dataSets);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setLabelCount(5, true);

        YAxis yAxisleft = chart.getAxisLeft();

        YAxis yAxisright = chart.getAxisRight();
        yAxisright.setDrawLabels(false);
        yAxisright.setDrawAxisLine(false);
        yAxisright.setDrawGridLines(false);

        chart.setData(data);
        chart.invalidate();
    }

    private void Defalut_Location(){
        LatLng Seoul = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Seoul);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Seoul, 18));
        mMap.clear();
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        String String_choose_child = Integer.toString(choose_child_int);
        Bitmap bitmap = getBitmapFromCache(String_choose_child+"child.png");
        String mSerial_number = preferences.getString(String_choose_child+"Child_serial", "No_serial");

        MarkerEvent(mSerial_number, bitmap);
        return true;
    }

    private void MarkerEvent(String SerialNumber, Bitmap bitmap){

        Get_Perodic_value(SerialNumber, bitmap);
    }


    /*

    Intent ShowPath = new Intent(Main_Activity.this, GoogleMap_ShowPath.class);
                    ShowPath.putExtra("GPS_Value", PmGPS);
                    startActivity(ShowPath);

    private void MarkerEvent(String SerialNumber, Bitmap bitmap){

        DatabaseReference Marker = mDatabase.child("users").child(SerialNumber);
        Marker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    users group = snapshot.getValue(users.class);
                    Map data = group.getData();

                    String string_number = Integer.toString(choose_child_int);
                    String child_name = preferences.getString(string_number + "Child_Name", "No_name");

                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);

                    DateFormat YeardateFormat = new SimpleDateFormat("yyyy");
                    String StringYear = YeardateFormat.format(mDate);

                    DateFormat MonthdateFormat = new SimpleDateFormat("MM");
                    String StringMonth = MonthdateFormat.format(mDate);

                    DateFormat DaydateFormat = new SimpleDateFormat("dd");
                    String StringDay = DaydateFormat.format(mDate);

                    DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
                    String StringTime = TimedateFormat.format(mDate);

                    Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                    Map<String, Object> YearData = (Map<String, Object>) PeriodicData.get(StringYear);
                    Map<String, Object> MonthData = (Map<String, Object>) YearData.get("06");
                    Map<String, Object> DayData = (Map<String, Object>) MonthData.get("25");
                    Map<String, Object> TimeData = null;

                    Map<String, Object> TimeMonthdata = (Map<String, Object>) YearData.get("06");
                    Map<String, Object> TimeDaydata = (Map<String, Object>) TimeMonthdata.get("25");

                    String mGPS = null;

                    new_lat = 0;
                    new_lot = 0;

                    for (int j = 15; j < 16; j++) {
                        for (int k = 0; k < 60; k += 10) {
                            TimeData = (Map<String, Object>) DayData.get(Integer.toString(j) + ":" + String.format("%02d", k) + ":00");
                            Log.d("TimeData", Integer.toString(j) + ":" + Integer.toString(k) + ":00" + TimeData);
                            mGPS = TimeData.get("GPS").toString();
                        }
                    }

                    String Str_replace = mGPS.replace(",", "");
                    String SStr_replace = Str_replace.replace("[", "");
                    String SSStr_replace = SStr_replace.replace("]", "");

                    String[] Day_SplitStr = SSStr_replace.split(" ");

                    for (int j = 0; j < Day_SplitStr.length; j++) {
                        Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");
                    }

                    new_lat = Float.parseFloat(Day_SplitStr[0]);
                    new_lot = Float.parseFloat(Day_SplitStr[1]);

                    LatLng Location = new LatLng(new_lat, new_lot);

                    Marker WalkMarker = mMap.addMarker(new MarkerOptions()
                            .position(Location)
                            .title(String.format("%.0f", calMinute) + " 분전")
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

                    WalkMarker.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 18));

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

     */

    /*
    private ArrayList<Entry> Day_dataValue(ArrayList<String> timearray, int mode) {

        ArrayList<Entry> Dayvals = new ArrayList<Entry>();

        for(int i = 0; i<timearray.size(); i++){
            String database_dayval = timearray.get(i);

            String Str_replace = database_dayval.replace(",", "=");
            String SStr_replace = Str_replace.replace(" ", "");
            String SSStr_replace = SStr_replace.replace("{", "");
            String SSSStr_replace = SSStr_replace.replace("}", "");
            String[] Day_SplitStr = SSSStr_replace.split("=");

            for(int j = 0; j < Day_SplitStr.length; j++) {
                Log.d("PARSING", "DAYVAL: "+Day_SplitStr[j]);
            }
            if(mode == 1) //걷기 속도
                Dayvals.add(new Entry(i, Float.parseFloat(Day_SplitStr[mode])));

            else if(mode == 2) { //분당 속도
                mode = 1;
                float avrage = 60;
                Dayvals.add(new Entry(i, ((Float.parseFloat(Day_SplitStr[mode])) / avrage)));
            }
            else if(mode == 3) //스텝 카운트
                Dayvals.add(new Entry(i,Float.parseFloat(Day_SplitStr[mode])));

            else{ // 거리
                mode = 3;
                Dayvals.add(new Entry(i,((Float.parseFloat(Day_SplitStr[mode])) * (float)0.4 )));
            }
        }
        return Dayvals;
    }

     */

    /*
                            Day_arrData.clear();
                            arData_1.clear();

                            int Day_Step_count=0;
                            float Day_Walk_Speed = 0, Day_Run_Speed = 0, Day_Distance = 0;

                            child_walk_speed.setText("");
                            child_run_speed.setText("");
                            child_distance.setText("");
                            child_stepcount.setText("");

                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String key = snapshot1.getKey();
                                String data = snapshot1.getValue().toString();
                                arData_1.add(key);
                                Day_arrData.add(data);
                            }

                            for(int i=0; i<Day_arrData.size();i++) {
                                String database_dayval = Day_arrData.get(i);

                                String Str_replace = database_dayval.replace(",", "=");
                                String SStr_replace = Str_replace.replace(" ", "");
                                String SSStr_replace = SStr_replace.replace("{", "");
                                String SSSStr_replace = SSStr_replace.replace("}", "");
                                String[] Day_SplitStr = SSSStr_replace.split("=");

                                for(int j = 0; j < Day_SplitStr.length; j++) {
                                    Log.d("SPINNER", "onDataChange: "+Day_SplitStr[j]+"\n");
                                }

                                Day_Walk_Speed += Float.parseFloat(Day_SplitStr[1]);
                                Day_Step_count += Integer.parseInt(Day_SplitStr[3]);

                            }
                            Day_Run_Speed = Day_Walk_Speed / (float)60.0;
                            Day_Walk_Speed /= Day_arrData.size();

                            Day_Distance = Day_Step_count * (float)0.4;

                            child_stepcount.setText("    "+Integer.toString(Day_Step_count)+" 보");
                            child_walk_speed.setText("    "+Float.toString(Day_Walk_Speed)+" m/s");
                            child_run_speed.setText("    "+Float.toString(Day_Run_Speed)+" m/s");
                            child_distance.setText("    "+Float.toString(Day_Distance)+" m");
     */

     /*
    private void get_firebase_val(String mSerial_number, Bitmap bitmap){
        DatabaseReference mDay = mDatabase.child("users").child(mSerial_number).child("data").child("Periodic").child("2021").child("06").child("25");
        mDay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Day_arrData.clear();
                arData_1.clear();

                String string_number = Integer.toString(choose_child_int);
                String child_name = preferences.getString(string_number+"Child_Name","No_name");

                float new_lat = 0, new_lot = 0;

                int Day_Step_count=0;
                float Day_Walk_Speed = 0, Day_Run_Speed = 0, Day_Distance=0;

                child_walk_speed.setText("");
                child_run_speed.setText("");
                child_distance.setText("");
                child_stepcount.setText("");

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String key = snapshot1.getKey();
                    String data = snapshot1.getValue().toString();
                    arData_1.add(key);
                    Day_arrData.add(data);
                }

                for(int i=0; i<Day_arrData.size();i++) {
                    String database_dayval = Day_arrData.get(i);

                    String Str_replace = database_dayval.replace(",", "=");
                    String SStr_replace = Str_replace.replace(" ", "");
                    String SSStr_replace = SStr_replace.replace("{", "");
                    String SSSStr_replace = SSStr_replace.replace("}", "");
                    String SSSSStr_replace = SSSStr_replace.replace("[", "");
                    String SSSSSStr_replace = SSSSStr_replace.replace("]", "");
                    String[] Day_SplitStr = SSSSSStr_replace.split("=");

                    for(int j = 0; j < Day_SplitStr.length; j++) {
                        //Log.d("GPS", "onDataChange: "+Day_SplitStr[j]+"\n");
                        new_lat = Float.parseFloat(Day_SplitStr[5]);
                        new_lot = Float.parseFloat(Day_SplitStr[6]);
                    }

                    Day_Walk_Speed += Float.parseFloat(Day_SplitStr[1]);
                    Day_Step_count += Integer.parseInt(Day_SplitStr[3]);
                }

                Day_Run_Speed = Day_Walk_Speed / (float) 60.0;
                Day_Walk_Speed /= Day_arrData.size();

                Day_Distance = Day_Step_count * (float) 0.4;

                child_stepcount.setText("    "+Integer.toString(Day_Step_count)+" 보");
                child_walk_speed.setText("    "+Float.toString(Day_Walk_Speed)+" m/s");
                child_run_speed.setText("    "+Float.toString(Day_Run_Speed)+" m/s");
                child_distance.setText("    "+Float.toString(Day_Distance)+" m");

                LatLng Location = new LatLng(new_lat, new_lot);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(Location);
                markerOptions.title(child_name);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 18));
                Last_location = Location;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void Get_Perodic_value(String mSerial_number, Bitmap bitmap){
        DatabaseReference mDay = mDatabase.child("users").child(mSerial_number);
        mDay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    users group = snapshot.getValue(users.class);
                    Map data = group.getData();

                    String string_number = Integer.toString(choose_child_int);
                    String child_name = preferences.getString(string_number+"Child_Name","No_name");

                    GetMotionData.clear();

                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);

                    DateFormat YeardateFormat = new SimpleDateFormat("yyyy");
                    String StringYear = YeardateFormat.format(mDate);

                    DateFormat MonthdateFormat = new SimpleDateFormat("MM");
                    String StringMonth = MonthdateFormat.format(mDate);

                    DateFormat DaydateFormat = new SimpleDateFormat("dd");
                    String StringDay = DaydateFormat.format(mDate);

                    DateFormat TimedateFormat = new SimpleDateFormat("HH:mm:ss");
                    String StringTime = TimedateFormat.format(mDate);

                    Map<String, Object> PeriodicData = (Map<String, Object>) data.get("Periodic");
                    Map<String, Object> YearData = (Map<String, Object>) PeriodicData.get(StringYear);
                    Map<String, Object> MonthData = (Map<String, Object>) YearData.get("06");
                    Map<String, Object> DayData = (Map<String, Object>) MonthData.get("25");
                    Map<String, Object> TimeData = null;

                    Map<String, Object> TimeMonthdata = (Map<String, Object>) YearData.get("06");
                    Map<String, Object> TimeDaydata = (Map<String, Object>) TimeMonthdata.get("25");

                    String mGPS = null;

                    new_lat = 0;
                    new_lot = 0;

                    float Last_lat = 0;
                    float Last_lot = 0;

                    double distance = 0;

                    int PeriodicHour = 0;
                    int PeriodicMin = 0;

                    for (int j = 15; j < 16; j++) {
                        for (int k = 0; k < 60; k += 10) {
                            TimeData = (Map<String, Object>) DayData.get(Integer.toString(j) + ":" + String.format("%02d", k) + ":00");
                            Log.d("TimeData", Integer.toString(j) + ":" + Integer.toString(k) + ":00" + TimeData);
                            mGPS = TimeData.get("GPS").toString();

                            PeriodicHour = j;
                            PeriodicMin = k;


                        }
                    }

                    String Str_replace = mGPS.replace(",", "");
                    String SStr_replace = Str_replace.replace("[", "");
                    String SSStr_replace = SStr_replace.replace("]", "");

                    String[] Day_SplitStr = SSStr_replace.split(" ");

                    for (int j = 0; j < Day_SplitStr.length; j++) {
                        Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");
                    }

                    new_lat = Float.parseFloat(Day_SplitStr[0]);
                    new_lot = Float.parseFloat(Day_SplitStr[1]);

                    distance = getDistance(new_lat, new_lot, Last_lat, Last_lot);
                    if(distance >= 100){
                        GetMotionData.add(Integer.toString(PeriodicHour) + ":" + Integer.toString(PeriodicMin) + ":00 "+child_name+" (이)가 출발 했습니다.");
                    }

                    /*

                    if(Time == "Day") {
                        // 실제 데이터에선 오늘 날짜로부터 하루 뺀 날짜부터 현재 시간까지
                        for (int i = 25; i < 26; i++) {
                            DayData = (Map<String, Object>) MonthData.get(Integer.toString(i));
                            Log.d("DayData", Integer.toString(i) + DayData);
                            for (int j = 15; j < 16; j++) {
                                for (int k = 0; k < 60; k += 10) {
                                    TimeData = (Map<String, Object>) DayData.get(Integer.toString(j) + ":" + String.format("%02d", k) + ":00");
                                    Log.d("TimeData", Integer.toString(j) + ":" + Integer.toString(k) + ":00" + TimeData);

                                    mGPS = TimeData.get("GPS").toString();
                                }
                            }
                        }

                        String Str_replace = mGPS.replace(",", "");
                        String SStr_replace = Str_replace.replace("[", "");
                        String SSStr_replace = SStr_replace.replace("]", "");

                        String[] Day_SplitStr = SSStr_replace.split(" ");

                        for (int j = 0; j < Day_SplitStr.length; j++) {
                            Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");
                        }

                        new_lat = Float.parseFloat(Day_SplitStr[0]);
                        new_lot = Float.parseFloat(Day_SplitStr[1]);

                    }

                    else if(Time == "Week") {

                        // 실제 데이터에선 오늘 날짜로부터 일주일 뺀 날짜로부터 현재 시간까지
                        for (int i = 25; i < 26; i++) {
                            DayData = (Map<String, Object>) MonthData.get(Integer.toString(i));
                            Log.d("DayData", Integer.toString(i) + DayData);
                            for (int j = 15; j < 16; j++) {
                                for (int k = 0; k < 60; k += 10) {
                                    TimeData = (Map<String, Object>) DayData.get(Integer.toString(j) + ":" + String.format("%02d", k) + ":00");
                                    Log.d("TimeData", Integer.toString(j) + ":" + Integer.toString(k) + ":00" + TimeData);

                                    mGPS = TimeData.get("GPS").toString();

                                }
                            }
                        }

                        String Str_replace = mGPS.replace(",", "");
                        String SStr_replace = Str_replace.replace("[", "");
                        String SSStr_replace = SStr_replace.replace("]", "");

                        String[] Day_SplitStr = SSStr_replace.split(" ");

                        for (int j = 0; j < Day_SplitStr.length; j++) {
                            Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");
                        }

                        new_lat = Float.parseFloat(Day_SplitStr[0]);
                        new_lot = Float.parseFloat(Day_SplitStr[1]);

                    }

                    else {
                        for (int i = 25; i < 26; i++) {
                            DayData = (Map<String, Object>) MonthData.get(Integer.toString(i));
                            Log.d("DayData", Integer.toString(i) + DayData);
                            for (int j = 15; j < 16; j++) {
                                for (int k = 0; k < 60; k += 10) {
                                    TimeData = (Map<String, Object>) DayData.get(Integer.toString(j) + ":" + String.format("%02d", k) + ":00");
                                    Log.d("TimeData", Integer.toString(j) + ":" + Integer.toString(k) + ":00" + TimeData);

                                    mGPS = TimeData.get("GPS").toString();

                                }
                            }
                        }

                        String Str_replace = mGPS.replace(",", "");
                        String SStr_replace = Str_replace.replace("[", "");
                        String SSStr_replace = SStr_replace.replace("]", "");

                        String[] Day_SplitStr = SSStr_replace.split(" ");

                        for (int j = 0; j < Day_SplitStr.length; j++) {
                            Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");
                        }

                        new_lat = Float.parseFloat(Day_SplitStr[0]);
                        new_lot = Float.parseFloat(Day_SplitStr[1]);

                    }

                    LatLng Location = new LatLng(new_lat, new_lot);
                    MarkerOptions WalkMarker = new MarkerOptions();
                    WalkMarker.position(Location);
                    WalkMarker.title(String.format("%.0f", calMinute) +" 분전");


                    WalkMarker.snippet("하이");
                    WalkMarker.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    mMap.addMarker(WalkMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 16));
                    Last_location = Location;


      */
                   /*

                    LatLng Location = new LatLng(new_lat, new_lot);

                    Marker WalkMarker = mMap.addMarker(new MarkerOptions()
                            .position(Location)
                            .title(String.format("%.0f", calMinute) +" 분전")
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

                    WalkMarker.showInfoWindow();
                    //mMap.addMarker(WalkMarker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 18));
                    Last_location = Location;

                    Last_lat = new_lat;
                    Last_lot = new_lot;

                    Alarm_banner();


                    //showNoti("minseok");



                    if(PeriodicData_alarm == true){
                        Alarm_banner("minseok", (float)0.7);
                    }



                }catch (NullPointerException e){
                    Log.d("mPeriodic", "nullpointerror: "+e);
                } catch (Exception e){
                    Log.d("mPeriodic", "exception: "+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

      */

    /*

                    for(int i = 0; i <mGPS.size(); i++){
                        Log.d("Main_mGPS", "Lat: "+mGPS.get(i));
                        new_lat = Float.parseFloat(mGPS.get(0).toString());
                        new_lot = Float.parseFloat(mGPS.get(1).toString());

                        new_lat_1 = Float.parseFloat(mGPS.get(2).toString());
                        new_lot_1 = Float.parseFloat(mGPS.get(3).toString());
                    }

                    Log.d("new_lat", Float.toString(new_lat));
                    Log.d("new_lot", Float.toString(new_lot));

                    Log.d("new_lat1", Float.toString(new_lat_1));
                    Log.d("new_lot1", Float.toString(new_lot_1));

                     */



                    /*
                    String Str_replace = mGPS.replace(",", "");
                    String SStr_replace = Str_replace.replace("[", "");
                    String SSStr_replace = SStr_replace.replace("]", "");

                    String[] Day_SplitStr = SSStr_replace.split(" ");

                    for (int j = 0; j < Day_SplitStr.length; j++) {
                        Log.d("SPINNER", "onDataChange: " + Day_SplitStr[j] + "\n");

                        if( j % 2 == 0){
                            gLat.add(Day_SplitStr[j]);
                            Log.d("SPINNER_2", "onDataChange: " + Day_SplitStr[j] + "\n");
                        }
                        else {
                            gLot.add(Day_SplitStr[j]);
                            Log.d("SPINNER_1", "onDataChange: " + Day_SplitStr[j] + "\n");
                        }

                    }

                     */

}
