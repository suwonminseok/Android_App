package com.intervoid.parent_app_ver2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import com.kakao.auth.AuthType;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Login_Activity extends AppCompatActivity{

    private ImageView Logo_Image;
    private Button Fake_naverButton;
    private Button FakeKakaoButton;
    private Button FakeGoogleButton;

    private SignInButton Login_with_Google;

    private CheckBox Autologin_box;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private SessionCallback callback;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    String token = "";
    String name = "";

    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;

    public static Context mContext;

    public int Login_info = 0;

    boolean LoginChecked;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle saveInstaceState) {
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_login);

        Login_with_Google = findViewById(R.id.Google_login);

        Autologin_box = (CheckBox) findViewById(R.id.Auto_login_Checkbox);
        Logo_Image = (ImageView) findViewById(R.id.Logo_Image);

        Fake_naverButton = (Button) findViewById(R.id.Fake_naver);
        FakeGoogleButton = (Button) findViewById(R.id.Fake_google);
        FakeKakaoButton = (Button) findViewById(R.id.Fake_Kakao);

        sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Glide.with(Login_Activity.this)
                .load(R.drawable.berhmkorea)
                .apply(new RequestOptions().centerCrop())
                .circleCrop()
                .into(Logo_Image);

        /////////////////////////////////google login//////////////////////////////////////////////

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Login_Activity.this, gso);

        Login_with_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Login_info = 1;
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        FakeGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Login_info = 1;
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        /*
        FakeGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Fake_google:
                        Login_with_Google.performClick();
                        break;
                }
            }
        });

         */

        /////////////////////////////////google login//////////////////////////////////////////////

        /////////////////////////////////kakao login///////////////////////////////////////////////

        FakeKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                callback = new SessionCallback();
                Session.getCurrentSession().addCallback(callback);

                 */

                Session session = Session.getCurrentSession();
                session.addCallback(new SessionCallback());
                session.open(AuthType.KAKAO_LOGIN_ALL, Login_Activity.this);
            }
        });

        /////////////////////////////////kako_login////////////////////////////////////////////////

        /////////////////////////////////Naver_login///////////////////////////////////////////////

        mContext = Login_Activity.this;

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name));

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.Naver_login);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        Fake_naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginButton.performClick();
            }
        });

        /*
        Fake_naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Fake_naver:
                        mOAuthLoginButton.performClick();
                        break;
                }
            }
        });

         */

        /////////////////////////////////Naver_login///////////////////////////////////////////////



        Autologin_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    LoginChecked = true;
                    editor.putBoolean("Check_BOX", true);
                }
                else{
                    LoginChecked = false;
                    editor.putBoolean("Check_BOX", false);
                }

                editor.commit();
            }
        });
    }

    private void AutoLogin(String id, String name) {
        SharedPreferences pref = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", id);
        editor.putString("name", name);
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            updataUI(firebaseUser);
                            Toast.makeText(Login_Activity.this, "성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_Activity.this, "실패", Toast.LENGTH_SHORT).show();
                            updataUI(null);
                        }
                    }
                });
    }

    private void updataUI(FirebaseUser user) {
        if(user != null) {

            SharedPreferences sharedPreferences1 = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor UserEditor = sharedPreferences1.edit();

            Uri uri = user.getPhotoUrl();
            String struri = uri.toString();
            UserEditor.putString("Uri", struri);
            UserEditor.commit();
            Log.d("struri", "updataUI: "+struri);
            Intent intent = new Intent(this, Main_Activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean AutologinVal = sharedPreferences.getBoolean("Check_BOX", false);
        int LoginInfo = sharedPreferences.getInt("LoginInfo", 1);

        if(AutologinVal == true && LoginInfo == 1){
            mUser = mAuth.getCurrentUser();
            if(mUser!=null){
                startActivity(new Intent(Login_Activity.this, Main_Activity.class));
                finish();
            }
        }
        else if(AutologinVal == true && LoginInfo == 2){
            OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {

                    if(success){
                    String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                    Intent Naver_logint = new Intent(Login_Activity.this, Main_Activity.class);
                    startActivity(Naver_logint);
                    finish();
                    }
                    else {
                        String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                        String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                        Toast.makeText(mContext, "errorCode"+errorCode+", errorDesc:"+errorDesc, Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }

        else if(AutologinVal == true && Login_info == 3){
            loadShared();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null){
                Log.e("onSessionOpenFailed", String.valueOf(exception));
            }
            setContentView(R.layout.activity_login);
        }
    }

    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d("onFailure", message);
                //Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
            } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(final UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                Login_info = 3;
                //Logger.d("UserProfile : " + userProfile);
                Log.d("UserProfile", "UserProfile : "+userProfile);

                Log.d("kakao_login", "유저가입성공");
                // Create a new user with a first and last name
                // 유저 카카오톡 아이디 디비에 넣음(첫가입인 경우에만 디비에저장)

                Map<String, String> user = new HashMap<>();
                user.put("token", userProfile.getId() + "");
                user.put("name", userProfile.getNickname());
                firestore.collection("users")
                        .document(userProfile.getId() + "")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("login_kakao", "유저정보 디비삽입 성공");
                                saveShared(userProfile.getId() + "", userProfile.getNickname());

                            }
                        });
                redirectHomeActivity(); // 로그인 성공시 MainActivity로

            }
        });
    }

    private void redirectHomeActivity() {
        startActivity(new Intent(this, Login_Activity.class));
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, Main_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /*쉐어드에 입력값 저장*/
    private void saveShared( String id, String name) {
        SharedPreferences pref = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", id);
        editor.putString("name", name);
        editor.apply();
    }

    /*쉐어드값 불러오기*/
    private void loadShared() {
        SharedPreferences pref = getSharedPreferences("profile", MODE_PRIVATE);
        token = pref.getString("token", "");
        name = pref.getString("name", "");
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(success){
                Login_info = 2;
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                editor.putString("Naver_Token", accessToken);
                editor.putString("Naver_refreshToken", refreshToken);
                editor.putLong("Naver_At", expiresAt);
                editor.putString("Naver_Type", tokenType);
                editor.commit();

                Intent Naver_logint = new Intent(Login_Activity.this, Main_Activity.class);
                startActivity(Naver_logint);
                finish();

            } else{
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode"+errorCode+", errorDesc:"+errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void GoogleSignIn() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient.asGoogleApiClient());
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    /*

    public void onClick(View v) {
        if(v == FakeGoogleButton) {
            GoogleSignIn();
        }else if(v == Fake_naverButton) {
            mOAuthLoginButton.performClick();
        }else if(v == FakeKakaoButton){
            Fake_naverButton.performClick();
        }
    }

     */

}
