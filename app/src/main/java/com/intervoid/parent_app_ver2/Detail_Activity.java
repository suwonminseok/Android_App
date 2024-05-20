package com.intervoid.parent_app_ver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class Detail_Activity extends AppCompatActivity {

    private ImageView Child_image;

    private EditText Child_name;

    private ImageButton Check_name;
    private ImageButton Child_call;
    private ImageButton Child_message;
    private ImageButton Child_color;
    private ImageButton backbutton;

    private TextView Child_ID;
    private TextView Child_seial;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    int choice_color;
    String child_number;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_child_detatil_profile);

        Child_image = (ImageView) findViewById(R.id.detail_child_image);
        Child_name = (EditText) findViewById(R.id.detail_edit_name);
        // Check_name = (ImageButton) findViewById(R.id.detail_name_check_button);
        Child_call = (ImageButton) findViewById(R.id.detail_call);
        Child_message = (ImageButton) findViewById(R.id.detail_message);
        Child_color = (ImageButton) findViewById(R.id.detail_color);
        backbutton = (ImageButton) findViewById(R.id.detail_backbutton);
        Child_ID = (TextView) findViewById(R.id.detail_child_id);
        Child_seial = (TextView) findViewById(R.id.detail_serial);

        Intent getIntent = getIntent();

        int get_child = getIntent.getExtras().getInt("choose_child");
        String get_childstring = Integer.toString(get_child);
        preferences = getSharedPreferences("child", MODE_PRIVATE);

        Detail_child(get_childstring);

        /*
        Check_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewName = Child_name.getText().toString().trim();
                editor = preferences.edit();
                editor.putString(get_childstring+"Child_Name", NewName);
                editor.commit();
                Child_name.setText(NewName);
            }
        });

         */

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailIntent = new Intent(Detail_Activity.this, Main_Activity.class);
                DetailIntent.putExtra("choose_color", choice_color);
                startActivity(DetailIntent);
                finish();
            }
        });

        Child_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColroPicker();
            }
        });

        Child_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = "tel:"+child_number;
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
                startActivity(callIntent);
            }
        });

        Child_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = "sms:"+child_number;
                Intent smsintent = new Intent(Intent.ACTION_SENDTO, Uri.parse(sms));
                startActivity(smsintent);
            }
        });

    }

    private void Detail_child(String number){

        String child_usrstring = preferences.getString(number+"uri", "Nouri");
        Uri image_uri = Uri.parse(child_usrstring);

        Glide.with(this)
                .load(image_uri)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(Child_image);

        String child_name = preferences.getString(number+"Child_Name", "NoName");
        String child_ID = preferences.getString(number+"Child_ID", "NoID");
        String child_serial = preferences.getString(number+"Child_serial", "NoSerial");
        child_number = preferences.getString(number + "Child_Number", "NoNumber");

        Child_name.setText(child_name);
        Child_ID.setText(child_ID);
        Child_seial.setText(child_serial);
    }

    public void openColroPicker() {
        final ColorPicker colorPicker = new ColorPicker(Detail_Activity.this);
        ArrayList<String> colors = new ArrayList<>();

        colors.add("#FFAB91");
        colors.add("#F48FB1");
        colors.add("#CE93D8");
        colors.add("#B39DDB");
        colors.add("#9FA8DA");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        choice_color = color;
                        Child_color.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }


}
