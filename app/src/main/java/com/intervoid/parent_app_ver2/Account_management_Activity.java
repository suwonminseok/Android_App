package com.intervoid.parent_app_ver2;

import android.app.AlertDialog;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.response.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.net.URI;

public class Account_management_Activity extends AppCompatActivity {

    private Button logout_buttton;
    private Button account_deletebutton;
    private ImageButton backbutton;
    private ImageView UserInfo;

    private FirebaseAuth mAuth;

    public static OAuthLogin mOAuthLoginInstance;
    Context mContext;

    GoogleSignInClient mGoogleSignInClient;

    public int LogoutInfo = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences shared;

    private Uri personPhoto;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_account_management);

        mContext = getApplicationContext();

        sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        logout_buttton = (Button) findViewById(R.id.account_management_logoutbutton);
        account_deletebutton = (Button) findViewById(R.id.account_management_deletebutton);
        backbutton = (ImageButton) findViewById(R.id.account_management_backbutton);
        UserInfo = (ImageView) findViewById(R.id.account_management_imageview);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Account_management_Activity.this, gso);


        /*
        Intent UriInfo = getIntent();

        String uri = UriInfo.getExtras().getString("uri");
        Log.d("Uri", "onCreate: "+uri);
        personPhoto = Uri.parse(uri);

         */

        /*
        GoogleSignInAccount account = null;

        UserInfo.setImageURI(account.getPhotoUrl());

         */




        mAuth = FirebaseAuth.getInstance();

        LogoutInfo = ((Login_Activity)Login_Activity.mContext).Login_info;

        mOAuthLoginInstance = OAuthLogin.getInstance();
        //mOAuthLoginInstance.showDevelopersLog(true);
        //mOAuthLoginInstance.init(mContext, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name));

        SetImage(LogoutInfo);

        logout_buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+LogoutInfo);

                if(LogoutInfo == 1){
                    mAuth.signOut();
                    editor.putBoolean("Check_BOX", false);
                    startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));
                    finish();
                }

                else if(LogoutInfo == 2){
                    Log.d("TAG", "naver_logout");
                    mOAuthLoginInstance.logout(mContext);
                    editor.putBoolean("Check_BOX", false);
                    startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));
                    finish();
                }

                else {
                    UserManagement.requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));
                            editor.putBoolean("Check_BOX", false);
                            editor.commit();
                            finish();
                        }
                    });
                }
            }
        });



        account_deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_setting_custom_save(v, LogoutInfo);
                Log.d("TAG", "onClick: "+"delete");
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleSignResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if(acct != null) {
                personPhoto = acct.getPhotoUrl();
            }
        }catch (ApiException e){
            Log.d("ApiException", "handleSignResult: "+e.getStatusCode());
        }
    }

    private void SetImage(int KindLogin){
        if(KindLogin == 1){
            shared = getSharedPreferences("User", MODE_PRIVATE);
            String PhotoUri = shared.getString("Uri", "0");
            Glide.with(this).load(PhotoUri).circleCrop()
                    .into(UserInfo);

        }

        else if(KindLogin == 2){
            ;
        }
        else if(KindLogin == 3){
            ;
        }
    }

    public void onClick_setting_custom_save(View view, int i){
        SharedPreferences shared = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        SharedPreferences InfoShared = getSharedPreferences("child", MODE_PRIVATE);
        SharedPreferences.Editor InfoEditor = InfoShared.edit();

        SharedPreferences kakaoShared = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor kakaoedit = kakaoShared.edit();

        new AlertDialog.Builder(this)
                .setTitle("계정 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(i == 1){
                            Toast.makeText(Account_management_Activity.this, "계정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mGoogleSignInClient.revokeAccess();
                            editor.clear();
                            editor.commit();
                            InfoEditor.clear();
                            InfoEditor.commit();
                            startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));
                            finish();
                        }

                        else if(i == 2){
                            Toast.makeText(Account_management_Activity.this, "계정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mOAuthLoginInstance.logout(mContext);
                            editor.clear();
                            editor.commit();
                            InfoEditor.clear();
                            InfoEditor.commit();
                            startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));
                            finish();
                        }

                        else{
                            UserManagement.requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {

                                    Toast.makeText(Account_management_Activity.this, "계정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Account_management_Activity.this, Login_Activity.class));

                                    kakaoedit.clear();
                                    kakaoedit.apply();

                                    editor.clear();
                                    editor.commit();
                                    InfoEditor.clear();
                                    InfoEditor.commit();
                                    finish();
                                }
                            });
                        }
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Account_management_Activity.this, "취소합니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}
