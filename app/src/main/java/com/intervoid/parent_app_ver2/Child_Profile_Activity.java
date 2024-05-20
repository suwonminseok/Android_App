package com.intervoid.parent_app_ver2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Child_Profile_Activity extends AppCompatActivity {

    private ImageButton Child_Image_button;
    private Button Check_button;

    private EditText Seach_child_name;
    private EditText Seach_child_id;
    private EditText Seach_child_address;
    private EditText Seach_child_ssid;

    private final int GET_GALLERY_IMAGE = 200;

    public boolean Flag = false;

    private int number_of_childen;
    private String Stringnumber_of_child;

    private Bitmap bitmap = null;

    private String stringuri;

    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        SharedPreferences preferences = getSharedPreferences("child", MODE_PRIVATE);
        number_of_childen = preferences.getInt("child", 0);

        Stringnumber_of_child = Integer.toString(number_of_childen);

        SharedPreferences.Editor editor = preferences.edit();

        Child_Image_button = (ImageButton) findViewById(R.id.child_image);
        Check_button = (Button) findViewById(R.id.profile_check);
        Seach_child_name = (EditText) findViewById(R.id.Edit_child_name);
        Seach_child_id = (EditText) findViewById(R.id.Edit_child_id);
        Seach_child_address = (EditText) findViewById(R.id.Edit_child_address);
        Seach_child_ssid = (EditText) findViewById(R.id.Edit_child_ssid);

        Intent intent = getIntent();

        String Name = intent.getExtras().getString("Get_Name");
        String Address = intent.getExtras().getString("Get_Address");
        String ID = intent.getExtras().getString("Get_ID");
        String SSID = intent.getExtras().getString("Get_SSID");

        Seach_child_name.setText("    이름   : " + Name);
        Seach_child_address.setText("    주소   : " + Address);
        Seach_child_id.setText("    아이디 : " + ID);
        Seach_child_ssid.setText("    SSID : " + SSID);


        Child_Image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });


        Check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flag == true) {
                    editor.putString(Stringnumber_of_child+"uri", stringuri);
                    editor.putInt("child", number_of_childen);

                    /*

                    int number = preferences.getInt("child",0);
                    number++;
                    editor.putInt("child",number);

                     */

                    editor.commit();

                    Intent intent = new Intent(Child_Profile_Activity.this, Main_Activity.class);
                    intent.putExtra("child_increase", number_of_childen);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(Child_Profile_Activity.this, "사진을 등록해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Glide.with(this)
                    .load(selectedImageUri)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(Child_Image_button);
            Flag = !Flag;
            stringuri = selectedImageUri.toString();

            Save_Image(selectedImageUri);
        }
    }

    /*
    public void Save_Image(Uri uri) {

        try{
            Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .apply(new RequestOptions()
                    .override(100, 100).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(this, 1000,5,"#ffffff",10)))
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                            Bitmap bitmap = resource;
                            saveImage(bitmap);
                            return false;
                        }
                    }).submit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

     */

    public void Save_Image(Uri uri) {

        try{
            Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .override(90, 90).centerCrop().circleCrop()
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                            Bitmap bitmap = resource;
                            saveImage(bitmap);
                            return false;
                        }
                    }).submit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String saveImage(Bitmap image) {
        String saveImagePath = null;
        String imageFileName = Stringnumber_of_child+"child"+".png";
        File storageDir = getCacheDir();
        boolean success = true;
        if(!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            saveImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the image to the system gallery
            galleryAddPic(saveImagePath);
        }
        return saveImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(Child_Profile_Activity.this, "IMAGE SAVED", Toast.LENGTH_LONG).show();
    }

    private void remove_shared(int number_of_child){

        SharedPreferences preferences = getSharedPreferences("child", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = preferences.edit();
        String number = Integer.toString(number_of_child);

        editor.remove(number+"Child_ID");
        editor.remove(number+"Child_Name");
        editor.remove(number+"uri");
        editor.remove(number+"Child_serial");
        editor.remove(number+"Child_Number");

        editor.putInt("child",--number_of_child);
        editor.commit();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        remove_shared(number_of_childen);
    }

}


