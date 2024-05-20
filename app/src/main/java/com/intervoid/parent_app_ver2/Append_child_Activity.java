package com.intervoid.parent_app_ver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Append_child_Activity extends AppCompatActivity {

    private LinearLayout firstline;
    private LinearLayout secondline;
    private LinearLayout thirdline;
    private LinearLayout fourthline;

    private Button FirstChildInfo;
    private Button SecondChildInfo;
    private Button ThirdChildInfo;
    private Button FourthChildInfo;

    private Button ConnectChild;
    private Button first_child_dismiss;
    private Button second_child_dismiss;
    private Button third_child_dismiss;
    private Button fourth_child_dimiss;

    private ImageButton backbutton;

    SharedPreferences preferences;

    private int Number_of_child;

    private String numberString;

    private String first_name, second_name, third_name, fourth_name;
    private String first_ID, second_ID, third_ID, fourth_ID;
    private String first_serial, second_serial, third_serial, fourth_serial;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_append_child);

        ConnectChild = (Button) findViewById(R.id.append_connect_child);
        first_child_dismiss = (Button) findViewById(R.id.child_setting_first_dismissbutton);
        second_child_dismiss = (Button) findViewById(R.id.child_setting_second_dismissbutton);
        third_child_dismiss = (Button) findViewById(R.id.child_setting_third_dismissbutton);
        fourth_child_dimiss = (Button) findViewById(R.id.child_setting_fourth_dismissbutton);
        backbutton = (ImageButton) findViewById(R.id.child_append_backbutton);

        FirstChildInfo = (Button) findViewById(R.id.child_setting_first_id);
        SecondChildInfo = (Button) findViewById(R.id.child_setting_second_id);
        ThirdChildInfo = (Button) findViewById(R.id.child_setting_third_id);
        FourthChildInfo = (Button) findViewById(R.id.child_setting_fourth_id);

        firstline = (LinearLayout) findViewById(R.id.append_firstline);
        secondline = (LinearLayout) findViewById(R.id.append_secondline);
        thirdline = (LinearLayout) findViewById(R.id.append_thirdline);
        fourthline = (LinearLayout) findViewById(R.id.append_fouthline);

        ////////////////////////////////////////////////////////////////////////////////////////////

        preferences = getSharedPreferences("child", MODE_PRIVATE);
        Number_of_child = preferences.getInt("child", 0);
        numberString = Integer.toString(Number_of_child);

        switch (Number_of_child){
            case 0:

                firstline.setVisibility(View.INVISIBLE);
                secondline.setVisibility(View.INVISIBLE);
                thirdline.setVisibility(View.INVISIBLE);
                fourthline.setVisibility(View.INVISIBLE);

                break;


            case 1:

                first_name = preferences.getString(numberString+"Child_Name", "NoName");
                first_ID = preferences.getString(numberString+"Child_ID", "NoID");
                first_serial = preferences.getString(numberString+"Child_serial", "NoSerial");

                FirstChildInfo.setText("    "+first_name+"    "+first_ID+"    "+first_serial);

                FirstChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 1);
                        startActivity(ChildIntent);
                    }
                });

                secondline.setVisibility(View.INVISIBLE);
                thirdline.setVisibility(View.INVISIBLE);
                fourthline.setVisibility(View.INVISIBLE);

                break;

            case 2:

                first_name = preferences.getString("1Child_Name", "NoName");
                first_ID = preferences.getString("1Child_ID", "NoID");
                first_serial = preferences.getString("1Child_serial", "NoSerial");

                FirstChildInfo.setText("    "+first_name+"    "+first_ID+"    "+first_serial);

                FirstChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 1);
                        startActivity(ChildIntent);
                    }
                });

                secondline.setVisibility(View.VISIBLE);

                second_name = preferences.getString(numberString+"Child_Name", "NoName");
                second_ID = preferences.getString(numberString+"Child_ID", "NoID");
                second_serial = preferences.getString(numberString+"Child_serial", "NoSerial");

                SecondChildInfo.setText("    "+second_name+"    "+second_ID+"    "+second_serial);

                SecondChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 2);
                        startActivity(ChildIntent);
                    }
                });

                thirdline.setVisibility(View.INVISIBLE);
                fourthline.setVisibility(View.INVISIBLE);

                break;

            case 3:

                first_name = preferences.getString("1Child_Name", "NoName");
                first_ID = preferences.getString("1Child_ID", "NoID");
                first_serial = preferences.getString("1Child_serial", "NoSerial");

                FirstChildInfo.setText("    "+first_name+"    "+first_ID+" "   +first_serial);

                FirstChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 1);
                        startActivity(ChildIntent);
                    }
                });

                secondline.setVisibility(View.VISIBLE);

                second_name = preferences.getString("2Child_Name", "NoName");
                second_ID = preferences.getString("2Child_ID", "NoID");
                second_serial = preferences.getString("2Child_serial", "NoSerial");

                SecondChildInfo.setText("    "+second_name+"    "+second_ID+"    "+second_serial);

                SecondChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 2);
                        startActivity(ChildIntent);
                    }
                });

                thirdline.setVisibility(View.VISIBLE);

                third_name = preferences.getString(numberString+"Child_Name", "NoName");
                third_ID = preferences.getString(numberString+"Child_ID", "NoID");
                third_serial = preferences.getString(numberString+"Child_serial", "NoSerial");

                ThirdChildInfo.setText("    "+third_name +"    "+ third_ID+"    "+third_serial);

                ThirdChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 3);
                        startActivity(ChildIntent);
                    }
                });

                fourthline.setVisibility(View.INVISIBLE);

                break;

            case 4:

                firstline.setVisibility(View.VISIBLE);

                first_name = preferences.getString("1Child_Name", "NoName");
                first_ID = preferences.getString("1Child_ID", "NoID");
                first_serial = preferences.getString("1Child_serial", "NoSerial");

                FirstChildInfo.setText("    "+first_name+"    "+first_ID+"    "+first_serial);

                FirstChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 1);
                        startActivity(ChildIntent);
                    }
                });

                secondline.setVisibility(View.VISIBLE);

                second_name = preferences.getString("2Child_Name", "NoName");
                second_ID = preferences.getString("2Child_ID", "NoID");
                second_serial = preferences.getString("2Child_serial", "NoSerial");

                SecondChildInfo.setText("    "+second_name+"    "+second_ID+"    "+second_serial);

                SecondChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 2);
                        startActivity(ChildIntent);
                    }
                });

                thirdline.setVisibility(View.VISIBLE);

                third_name = preferences.getString("3Child_Name", "NoName");
                third_ID = preferences.getString("3Child_ID", "NoID");
                third_serial = preferences.getString("3Child_serial", "NoSerial");

                ThirdChildInfo.setText("    "+third_name +"    "+ third_ID+"    "+third_serial);

                ThirdChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 3);
                        startActivity(ChildIntent);
                    }
                });

                fourthline.setVisibility(View.VISIBLE);

                fourth_name = preferences.getString(numberString+"Child_Name", "NoName");
                fourth_ID = preferences.getString(numberString+"Child_ID", "NoID");
                fourth_serial = preferences.getString(numberString+"Child_serial", "NoSerial");

                FourthChildInfo.setText("    "+fourth_name+"    "+fourth_ID+"    "+fourth_serial);

                FourthChildInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ChildIntent = new Intent(Append_child_Activity.this, Detail_Activity.class);
                        ChildIntent.putExtra("choose_child", 4);
                        startActivity(ChildIntent);
                    }
                });

                break;

            default:
                Log.d("Switch", "onCreate: "+"4명 초과");
                break;
        }

        ConnectChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*

                preferences = getSharedPreferences("child", MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = preferences.edit();
                int number = preferences.getInt("child",0);
                number++;
                editor.putInt("child",number);
                editor.commit();

                 */
                Intent connectIntent = new Intent(Append_child_Activity.this, Getchild_Activity.class);
                startActivity(connectIntent);
                finish();
            }
        });

        first_child_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_shared(Number_of_child);
                Intent first_remove_Intent = new Intent(Append_child_Activity.this, Main_Activity.class);
                startActivity(first_remove_Intent);
                finish();
            }
        });

        second_child_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_shared(Number_of_child);
                Intent second_remove_Intent = new Intent(Append_child_Activity.this, Main_Activity.class);
                startActivity(second_remove_Intent);
                finish();
            }
        });

        third_child_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_shared(Number_of_child);
                Intent third_remove_Intent = new Intent(Append_child_Activity.this, Main_Activity.class);
                startActivity(third_remove_Intent);
                finish();
            }
        });

        fourth_child_dimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_shared(Number_of_child);
                Intent fourth_remove_Intent = new Intent(Append_child_Activity.this, Main_Activity.class);
                startActivity(fourth_remove_Intent);
                finish();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void remove_shared(int number_of_child){

        preferences = getSharedPreferences("child", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = preferences.edit();
        String number = Integer.toString(number_of_child);

        editor.remove(number+"Child_ID");
        editor.remove(number+"Child_Name");
        editor.remove(number+"uri");
        editor.remove(number+"Child_serial");
        editor.remove(number+"Child_Number");
        editor.remove(number+"Child_WalkAlarm");
        editor.remove(number+"Child_RunAlarm");

        editor.putInt("child",--number_of_child);
        editor.commit();
    }

}

