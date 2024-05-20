package com.intervoid.parent_app_ver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Getchild_Activity extends AppCompatActivity {

    private ImageButton back_button;
    private ImageButton Search_button;
    private Button Connet_button;

    private EditText seach_child_serial_number;

    private TextView ID_textview;
    private TextView ID_textview_val;
    private TextView Connect_textview;
    private TextView Connect_textview_val;

    private String Serial_Number;
    private String Get_ID;
    private String Get_SSID;
    private String Get_NAME;
    private String Get_ADDRESS;
    private String Get_Number;

    private String TestSerial;

    private int Child;
    private String FirstChildSerial;
    private String SecondChildSerial;
    private String ThirdChildSerial;
    private String FourthChildSerial;

    private int ChildNumber = 1;


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mGetSerial;
    DatabaseReference mGetValue;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private int children;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_getchild);

        back_button = (ImageButton) findViewById(R.id.Image_back_button);
        Search_button = (ImageButton) findViewById(R.id.seach_imagebutton);
        seach_child_serial_number = (EditText) findViewById(R.id.seach_edittext);
        Connet_button = (Button) findViewById(R.id.Connet_Button);

        ID_textview = (TextView) findViewById(R.id.id_textview);
        ID_textview_val = (TextView) findViewById(R.id.id_textview_value);
        Connect_textview = (TextView) findViewById(R.id.connet_text_view);
        Connect_textview_val = (TextView) findViewById(R.id.connet_text_view_value);

        sharedPreferences = getSharedPreferences("child", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Child = sharedPreferences.getInt("child", 0);

        FirstChildSerial = sharedPreferences.getString("1Child_serial", "NoSerialNumber");
        SecondChildSerial = sharedPreferences.getString("2Child_serial", "NoSerialNumber");
        ThirdChildSerial = sharedPreferences.getString("3Child_serial", "NoSerialNumber");
        FourthChildSerial = sharedPreferences.getString("4Child_serial", "NoSerialNumber");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Getchild_Activity.this, Main_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        /*

        if(Child == 1){
            FirstChildSerial = sharedPreferences.getString("1Child_serial", "Error");
            if(TestSerial.equals(FirstChildSerial)){
                Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                Log.d("Appned", FirstChildSerial);
                continue;
            }
        }else if(Child == 2){
            FirstChildSerial = sharedPreferences.getString("1Child_serial", "Error");
            SecondChildSerial = sharedPreferences.getString("2Child_serial", "Error");
            if(TestSerial.equals(FirstChildSerial) || TestSerial.equals(SecondChildSerial)){
                Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                Log.d("Appned", FirstChildSerial+SecondChildSerial);
                continue;
            }
        }else if(Child == 3){
            FirstChildSerial = sharedPreferences.getString("1Child_serial", "Error");
            SecondChildSerial = sharedPreferences.getString("2Child_serial", "Error");
            ThirdChildSerial = sharedPreferences.getString("3Child_serial", "Error");
            if(TestSerial.equals(FirstChildSerial) || TestSerial.equals(SecondChildSerial) ||
                    TestSerial.equals(ThirdChildSerial)){
                Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                Log.d("Appned", FirstChildSerial+SecondChildSerial+ThirdChildSerial);
                continue;
            }
        }else if(Child == 4){
            FirstChildSerial = sharedPreferences.getString("1Child_serial", "Error");
            SecondChildSerial = sharedPreferences.getString("2Child_serial", "Error");
            ThirdChildSerial = sharedPreferences.getString("3Child_serial", "Error");
            FourthChildSerial = sharedPreferences.getString("4Child_serial", "Error");
            if(TestSerial.equals(FirstChildSerial) || TestSerial.equals(SecondChildSerial) ||
                    TestSerial.equals(ThirdChildSerial) || TestSerial.equals(FourthChildSerial)){
                Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                Log.d("Appned", FirstChildSerial+SecondChildSerial+ThirdChildSerial+FourthChildSerial);
                continue;
            }
        }

         */

        Search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Serial_Number = seach_child_serial_number.getText().toString().trim();
                mGetSerial = mDatabase.child("users");
                mGetSerial.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            //users group = snapshot.getValue(users.class);
                            //TestID = group.getID();

                            //TestSerial = snapshot.getValue(String.class);
                            //getValue().toString();

                            for (DataSnapshot ds : snapshot.getChildren()) {
                                TestSerial = ds.getKey();

                                String CheckSerial = sharedPreferences.getString(ChildNumber+"Child_serial", "NoSerialNumber");

                                /*
                                if(TestSerial.equals(FirstChildSerial)){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    Log.d("FirstApeend", TestSerial +" "+ FirstChildSerial);
                                    continue;
                                }else if(TestSerial.equals(SecondChildSerial)){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    Log.d("SecondApeend", TestSerial +" "+ FirstChildSerial +" "+ SecondChildSerial);
                                    continue;
                                }else if(TestSerial.equals(ThirdChildSerial)){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    Log.d("ThridApeend", TestSerial +" "+ FirstChildSerial +" "+ SecondChildSerial
                                    + " " + ThirdChildSerial);
                                    continue;
                                }else if(TestSerial.equals(FourthChildSerial)){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    Log.d("FourthApeend", TestSerial +" "+ FirstChildSerial +" "+ SecondChildSerial
                                            + " " + ThirdChildSerial + " " +FourthChildSerial);
                                    continue;
                                }

                                 */

                                /*

                                if(CheckSerial != null){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    continue;
                                }

                                 */

                                if(TestSerial.equals(CheckSerial)){
                                    Toast.makeText(Getchild_Activity.this, "중복된 시리얼 번호입니다.", Toast.LENGTH_SHORT).show();
                                    ChildNumber++;
                                    continue;
                                }
                                else{
                                    CheckSerial(Serial_Number, TestSerial);
                                    Log.d("GETSERIAL", TestSerial);
                                    ChildNumber++;
                                    continue;


                                }

                            }

                            Log.d("GETSERIAL", TestSerial);
                        }catch (NullPointerException e){
                            e.printStackTrace();

                            Toast.makeText(Getchild_Activity.this, "틀림", Toast.LENGTH_SHORT).show();
                            seach_child_serial_number.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Log.d("Serial", Serial_Number);
                //Log.d("TestID", TestID);

            }
        });

        Connet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seach_child_serial_number.getText().toString().equals("")){
                    Toast.makeText(Getchild_Activity.this, "시리얼넘버를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                else if(ID_textview_val.getText().toString().equals("")){
                    Toast.makeText(Getchild_Activity.this, "시리얼넘버가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    Intent intent = new Intent(Getchild_Activity.this, Child_Profile_Activity.class);
                    intent.putExtra("Get_Name", Get_NAME);
                    intent.putExtra("Get_Address", Get_ADDRESS);
                    intent.putExtra("Get_ID", Get_ID);
                    intent.putExtra("Get_SSID", Get_SSID);
                    Search_button.setEnabled(true);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void CheckSerial(String SerialNumber, String CompareKey) {

        if(SerialNumber.equals(CompareKey)){
            Search_button.setEnabled(false);

            Log.d("GetChild", "onClick: "+mGetSerial);

            children = sharedPreferences.getInt("child", 0);
            children++;
            String number_of_child = Integer.toString(children);
            editor.putString(number_of_child+"Child_serial", Serial_Number);
            Log.d("serial_number", "onClick: "+Serial_Number);

            mGetValue = mDatabase.child("users").child(SerialNumber);

            mGetValue.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try{
                        users group = snapshot.getValue(users.class);
                        Get_ID = group.getID();
                        Log.d("TAG", "onDataChange: "+Get_ID);
                        Get_SSID = group.getSSID();
                        Get_ADDRESS = group.getAddress();
                        Get_NAME = group.getName();
                        Get_Number = group.getNumber();

                        ID_textview_val.setText(Get_ID);
                        Connect_textview_val.setText(Get_SSID);

                        editor.putString(number_of_child+"Child_ID", Get_ID);
                        editor.putString(number_of_child+"Child_Name", Get_NAME);
                        editor.putString(number_of_child+"Child_Number", Get_Number);
                        editor.putInt(number_of_child+"Child_RunAlarm", 1);
                        editor.putInt(number_of_child+"Child_WalkAlarm", 1);
                        editor.putInt("child", children);

                        editor.commit();

                    }catch (NullPointerException e){
                        Log.d("TAG", "onDataChange: "+ e);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Search_button.setEnabled(true);
    }
}
