package com.intervoid.parent_app_ver2;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;

public class Alarm_Activity extends AppCompatActivity implements Serializable {

    private RelativeLayout RunFirstChild;
    private RelativeLayout RunSecondChild;
    private RelativeLayout RunThirdChild;
    private RelativeLayout RunFourthChild;

    private RelativeLayout WalkFirstChild;
    private RelativeLayout WalkSecondChild;
    private RelativeLayout WalkThirdChild;
    private RelativeLayout WalkFourthChild;

    private TextView RunFirstName;
    private TextView RunSecondName;
    private TextView RunThirdName;
    private TextView RunFourthName;

    private TextView WalkFirstName;
    private TextView WalkSecondName;
    private TextView WalkThirdName;
    private TextView WalkFourthName;

    private Switch RunAllSwitch;
    private Switch RunFirstSwitch;
    private Switch RunSecondSwitch;
    private Switch RunThirdSwitch;
    private Switch RunFourthSwitch;

    private Switch WalkAllSwitch;
    private Switch WalkFirstSwitch;
    private Switch WalkSecondSwitch;
    private Switch WalkThirdSwitch;
    private Switch WalkFourthSwitch;

    private ImageButton BackButton;

    SharedPreferences sharedPreferences;

    int Get_Child = 0;
    String String_GetChild = null;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_alarm);

        BackButton = (ImageButton) findViewById(R.id.alarm_backbutton);

        RunFirstChild = (RelativeLayout) findViewById(R.id.Alarm_FirstRunlayout);
        RunSecondChild = (RelativeLayout) findViewById(R.id.Alarm_SecondRunlayout);
        RunThirdChild = (RelativeLayout) findViewById(R.id.Alarm_ThiredRunlayout);
        RunFourthChild = (RelativeLayout) findViewById(R.id.Alarm_FourthRunlayout);

        WalkFirstChild = (RelativeLayout) findViewById(R.id.Alarm_FirstWalklayout);
        WalkSecondChild = (RelativeLayout) findViewById(R.id.Alarm_SecondWalklayout);
        WalkThirdChild = (RelativeLayout) findViewById(R.id.Alarm_ThiredWalklayout);
        WalkFourthChild = (RelativeLayout) findViewById(R.id.Alarm_FourthWalklayout);

        RunFirstName = (TextView) findViewById(R.id.Alarm_FirstRunText);
        RunSecondName = (TextView) findViewById(R.id.Alarm_SecondRunText);
        RunThirdName = (TextView) findViewById(R.id.Alarm_ThiredRunText);
        RunFourthName = (TextView) findViewById(R.id.Alarm_FourthRunText);

        WalkFirstName = (TextView) findViewById(R.id.Alarm_FirstWalkText);
        WalkSecondName = (TextView) findViewById(R.id.Alarm_SecondWalkText);
        WalkThirdName = (TextView) findViewById(R.id.Alarm_ThiredWalkText);
        WalkFourthName = (TextView) findViewById(R.id.Alarm_FourthWalkText);

        RunAllSwitch = (Switch) findViewById(R.id.Alarm_ALL_RunSwitch);
        RunFirstSwitch = (Switch) findViewById(R.id.Alarm_FirstRunSwitch);
        RunSecondSwitch = (Switch) findViewById(R.id.Alarm_SecondRunSwitch);
        RunThirdSwitch = (Switch) findViewById(R.id.Alarm_ThiredRunSwitch);
        RunFourthSwitch = (Switch) findViewById(R.id.Alarm_FourthRunSwitch);

        WalkAllSwitch = (Switch) findViewById(R.id.Alarm_ALL_WalkSwitch);
        WalkFirstSwitch = (Switch) findViewById(R.id.Alarm_FirstWalkSwitch);
        WalkSecondSwitch = (Switch) findViewById(R.id.Alarm_SecondWalkSwitch);
        WalkThirdSwitch = (Switch) findViewById(R.id.Alarm_ThiredWalkSwitch);
        WalkFourthSwitch = (Switch) findViewById(R.id.Alarm_FourthWalkSwitch);

        sharedPreferences = getSharedPreferences("child", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent getIntent = getIntent();
        Get_Child = getIntent.getExtras().getInt("get_child");
        String_GetChild = Integer.toString(Get_Child);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RunAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    RunFirstSwitch.setChecked(true);
                    RunSecondSwitch.setChecked(true);
                    RunThirdSwitch.setChecked(true);
                    RunFourthSwitch.setChecked(true);
                }
                else{
                    RunFirstSwitch.setChecked(false);
                    RunSecondSwitch.setChecked(false);
                    RunThirdSwitch.setChecked(false);
                    RunFourthSwitch.setChecked(false);
                }
            }
        });

        WalkAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    WalkFirstSwitch.setChecked(true);
                    WalkSecondSwitch.setChecked(true);
                    WalkThirdSwitch.setChecked(true);
                    WalkFourthSwitch.setChecked(true);
                }
                else{
                    WalkFirstSwitch.setChecked(false);
                    WalkSecondSwitch.setChecked(false);
                    WalkThirdSwitch.setChecked(false);
                    WalkFourthSwitch.setChecked(false);
                }

            }
        });


        switch (Get_Child) {
            case 0:
                RunFirstChild.setVisibility(View.INVISIBLE);
                RunSecondChild.setVisibility(View.INVISIBLE);
                RunThirdChild.setVisibility(View.INVISIBLE);
                RunFourthChild.setVisibility(View.INVISIBLE);

                WalkFirstChild.setVisibility(View.INVISIBLE);
                WalkSecondChild.setVisibility(View.INVISIBLE);
                WalkThirdChild.setVisibility(View.INVISIBLE);
                WalkFourthChild.setVisibility(View.INVISIBLE);

                break;

            case 1:

                String FistName = sharedPreferences.getString("1Child_Name", "NoName");
                RunFirstName.setText(FistName);
                WalkFirstName.setText(FistName);

                Get_Position(1);

                RunFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("1Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("1Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RunSecondChild.setVisibility(View.INVISIBLE);
                RunThirdChild.setVisibility(View.INVISIBLE);
                RunFourthChild.setVisibility(View.INVISIBLE);


                WalkSecondChild.setVisibility(View.INVISIBLE);
                WalkThirdChild.setVisibility(View.INVISIBLE);
                WalkFourthChild.setVisibility(View.INVISIBLE);

                break;

            case 2:

                FistName = sharedPreferences.getString("1Child_Name", "NoName");
                RunFirstName.setText(FistName);
                WalkFirstName.setText(FistName);

                String SecondName = sharedPreferences.getString("2Child_Name", "NoName");
                RunSecondName.setText(SecondName);
                WalkSecondName.setText(SecondName);

                Get_Position(2);

                RunFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){

                            editor.putInt("1Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("1Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                RunSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                RunThirdChild.setVisibility(View.INVISIBLE);
                RunFourthChild.setVisibility(View.INVISIBLE);

                WalkThirdChild.setVisibility(View.INVISIBLE);
                WalkFourthChild.setVisibility(View.INVISIBLE);

                break;

            case 3:

                FistName = sharedPreferences.getString("1Child_Name", "NoName");
                RunFirstName.setText(FistName);
                WalkFirstName.setText(FistName);

                SecondName = sharedPreferences.getString("2Child_Name", "NoName");
                RunSecondName.setText(SecondName);
                WalkSecondName.setText(SecondName);

                String ThirdName = sharedPreferences.getString("3Child_Name", "NoName");
                RunSecondName.setText(ThirdName);
                WalkSecondName.setText(ThirdName);

                Get_Position(3);

                WalkFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("1Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                RunSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RunThirdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("3Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("3Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkThirdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("3Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("3Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RunFourthChild.setVisibility(View.INVISIBLE);

                WalkFourthChild.setVisibility(View.INVISIBLE);

                break;

            case 4:

                FistName = sharedPreferences.getString("1Child_Name", "NoName");
                RunFirstName.setText(FistName);
                WalkFirstName.setText(FistName);

                SecondName = sharedPreferences.getString("2Child_Name", "NoName");
                RunSecondName.setText(SecondName);
                WalkSecondName.setText(SecondName);

                ThirdName = sharedPreferences.getString("3Child_Name", "NoName");
                RunSecondName.setText(ThirdName);
                WalkSecondName.setText(ThirdName);

                String FourthName = sharedPreferences.getString("4Child_Name", "NoName");
                RunSecondName.setText(FourthName);
                WalkSecondName.setText(FourthName);

                Get_Position(4);

                WalkFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("1Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("1Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                RunSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("2Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("2Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RunThirdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("3Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("3Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkThirdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("3Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("3Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RunFourthSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("4Child_RunAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("4Child_RunAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "달리기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                WalkFourthSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            editor.putInt("4Child_WalkAlarm", 1);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putInt("4Child_WalkAlarm", 0);
                            editor.commit();
                            Toast.makeText(Alarm_Activity.this, "걷기 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

            default:

                Toast.makeText(Alarm_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Get_Position(int ChooseChild){

        if(ChooseChild == 1) {

            int FirstWalkAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);
            int FirstRunAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);


            if(FirstWalkAlarm == 1) {
                WalkFirstSwitch.setChecked(true);
                WalkAllSwitch.setChecked(true);
            }else {
                WalkFirstSwitch.setChecked(false);
                WalkAllSwitch.setChecked(false);
            }

            if(FirstRunAlarm == 1) {
                RunFirstSwitch.setChecked(true);
                RunAllSwitch.setChecked(true);
            }else {
                RunFirstSwitch.setChecked(false);
                RunAllSwitch.setChecked(false);
            }
        }

        else if(ChooseChild == 2){

            int FirstWalkAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);
            int FirstRunAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);

            int SecondWalkAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);
            int SecondRunAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);


            if(FirstWalkAlarm == 1) {
                WalkFirstSwitch.setChecked(true);
            }else {
                WalkFirstSwitch.setChecked(false);
            }

            if(FirstRunAlarm == 1) {
                RunFirstSwitch.setChecked(true);
            }else {
                RunFirstSwitch.setChecked(false);
            }

            if(SecondWalkAlarm == 1) {
                WalkSecondSwitch.setChecked(true);
            }else {
                WalkSecondSwitch.setChecked(false);
            }

            if(SecondRunAlarm == 1) {
                RunSecondSwitch.setChecked(true);
            }else {
                RunSecondSwitch.setChecked(false);
            }

            if(FirstWalkAlarm == 1 && SecondWalkAlarm == 1 ){
                WalkAllSwitch.setChecked(true);
            }else if(FirstWalkAlarm == 0 && SecondWalkAlarm == 0 ){
                WalkAllSwitch.setChecked(false);
            }

            if(FirstRunAlarm == 1 && SecondRunAlarm == 1 ) {
                RunAllSwitch.setChecked(true);
            } else if(FirstRunAlarm == 0 && SecondRunAlarm == 0 ){
                RunAllSwitch.setChecked(false);
            }
        }

        else if(ChooseChild == 3){

            String Number = Integer.toString(ChooseChild);

            int FirstWalkAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);
            int FirstRunAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);

            int SecondWalkAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);
            int SecondRunAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);

            int ThirdWalkAlarm = sharedPreferences.getInt("3Child_WalkAlarm", 1);
            int ThirdRunAlarm = sharedPreferences.getInt("3Child_WalkAlarm", 1);

            if(FirstWalkAlarm == 1) {
                WalkFirstSwitch.setChecked(true);
            }else
                WalkFirstSwitch.setChecked(false);

            if(FirstRunAlarm == 1)
                RunFirstSwitch.setChecked(true);
            else
                RunFirstSwitch.setChecked(false);

            if(SecondWalkAlarm == 1)
                WalkSecondSwitch.setChecked(true);
            else
                WalkSecondSwitch.setChecked(false);

            if(SecondRunAlarm == 1)
                RunSecondSwitch.setChecked(true);
            else
                RunSecondSwitch.setChecked(false);

            if(ThirdWalkAlarm == 1)
                WalkThirdSwitch.setChecked(true);
            else
                WalkThirdSwitch.setChecked(false);


            if(ThirdRunAlarm == 1)
                RunThirdSwitch.setChecked(true);
            else
                RunThirdSwitch.setChecked(false);

            if(FirstWalkAlarm == 1 && SecondWalkAlarm == 1 && ThirdWalkAlarm == 1 ){
                WalkAllSwitch.setChecked(true);
            }else if(FirstWalkAlarm == 0 && SecondWalkAlarm == 0 && ThirdWalkAlarm == 0 ){
                WalkAllSwitch.setChecked(false);
            }

            if(FirstRunAlarm == 1 && SecondRunAlarm == 1 && ThirdRunAlarm == 1 ) {
                RunAllSwitch.setChecked(true);
            } else if(FirstRunAlarm == 0 && SecondRunAlarm == 0 && ThirdRunAlarm == 0 ){
                RunAllSwitch.setChecked(false);
            }

        } else if(ChooseChild == 4){

            String Number = Integer.toString(ChooseChild);

            int FirstWalkAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);
            int FirstRunAlarm = sharedPreferences.getInt("1Child_WalkAlarm", 1);

            int SecondWalkAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);
            int SecondRunAlarm = sharedPreferences.getInt("2Child_WalkAlarm", 1);

            int ThirdWalkAlarm = sharedPreferences.getInt("3Child_WalkAlarm", 1);
            int ThirdRunAlarm = sharedPreferences.getInt("3Child_WalkAlarm", 1);

            int FourthWalkAlarm = sharedPreferences.getInt("4Child_WalkAlarm", 1);
            int FourthRunAlarm = sharedPreferences.getInt("4Child_WalkAlarm", 1);

            if(FirstWalkAlarm == 1)
                WalkFirstSwitch.setChecked(true);
            else
                WalkFirstSwitch.setChecked(false);

            if(FirstRunAlarm == 1)
                RunFirstSwitch.setChecked(true);
            else
                RunFirstSwitch.setChecked(false);

            if(SecondWalkAlarm == 1)
                WalkSecondSwitch.setChecked(true);
            else
                WalkFirstSwitch.setChecked(false);

            if(SecondRunAlarm == 1)
                RunSecondSwitch.setChecked(true);
            else
                RunFirstSwitch.setChecked(false);

            if(ThirdWalkAlarm == 1)
                WalkThirdSwitch.setChecked(true);
            else
                WalkThirdSwitch.setChecked(false);


            if(ThirdRunAlarm == 1)
                RunThirdSwitch.setChecked(true);
            else
                RunThirdSwitch.setChecked(false);

            if(FourthWalkAlarm == 1)
                WalkFourthSwitch.setChecked(true);
            else
                WalkFourthSwitch.setChecked(false);


            if(FourthRunAlarm == 1)
                RunFourthSwitch.setChecked(true);
            else
                RunFourthSwitch.setChecked(false);

            if(FirstWalkAlarm == 1 && SecondWalkAlarm == 1 && ThirdWalkAlarm == 1 && FourthWalkAlarm == 1){
                WalkAllSwitch.setChecked(true);
            }else if(FirstWalkAlarm == 0 && SecondWalkAlarm == 0 && ThirdWalkAlarm == 0 && FourthWalkAlarm == 0){
                WalkAllSwitch.setChecked(false);
            }

            if(FirstRunAlarm == 1 && SecondRunAlarm == 1 && ThirdRunAlarm == 1 && FourthRunAlarm == 1) {
                RunAllSwitch.setChecked(true);
            } else if(FirstRunAlarm == 0 && SecondRunAlarm == 0 && ThirdRunAlarm == 0 && FourthRunAlarm == 0){
                RunAllSwitch.setChecked(false);
            }

        }
    }
}
