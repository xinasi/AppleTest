package com.cyut.fruit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText edt_email;
    private EditText edt_password;
    private TextView textViewSingup, forgetPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics firebaseAnalytics;
    private ProgressDialog progressDialog;
    private  LinearLayout linearLayout;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference emailRef;
    /* 11/05 add google login */
    private GoogleSignInButton googleSignIn;
    private int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        edt_email = (EditText) findViewById(R.id.editTextEmail);
        edt_password = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSingup = (TextView) findViewById(R.id.textViewSignup);
        textViewSingup.setPaintFlags(textViewSingup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        linearLayout = (LinearLayout) findViewById(R.id.login_bk);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword) ;

        buttonSignIn.setOnClickListener(this);
        textViewSingup.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        edt_email.setOnClickListener(this);

        /* 11/05 設定Google登入Client */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder
                (getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Google 連線異常", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleSignIn = (GoogleSignInButton) findViewById(R.id.googleSignIn);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



    }

    private void userinput(){
        edt_email.setTextColor(Color.parseColor("#000000"));
    }

    private void userLogin(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            edt_email.setError("請輸入Email");
            //Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function exection further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            edt_password.setError("請輸入Password");
            //Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validations are OK
        //we will first show a progressbar
        progressDialog.setMessage("Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "登入失敗", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                /*
                String email = account.getEmail();
                String uid = account.getId();
                User user = new User(
                        email
                );
                FirebaseDatabase.getInstance().getReference("Data/User")
                        .child(uid)
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "寫入成功", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //display a failure message
                            Toast.makeText(LoginActivity.this, "寫入失敗", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                */
                 //取得使用者並試登入
                firebaseAuthWithGoogle(account);
            }
            else {
                Toast.makeText(LoginActivity.this, "錯誤，請檢查網路連線", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /* 使用Google登入Firebase */
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "錯誤，請檢查網路連線", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "歡迎: " + account.getDisplayName(), Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        //登入
        if (view == buttonSignIn){
            btnAnimation(buttonSignIn);
            Handler btnSignInHandler = new Handler();
            btnSignInHandler.postDelayed(btnSignInDelay, 450);
        }
        if(view == edt_email) {

        }
        //註冊
        if (view == textViewSingup){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
        //忘記密碼
        if (view == forgetPassword) {
            finish();
            startActivity(new Intent(this, ForgetPasswordActivity.class));
        }
    }
    /* 運行完動畫執行登入 */
    private Runnable btnSignInDelay = new Runnable() {
        public void run() {
            userLogin();
        }
    };

    /* 處理返回鍵事件 */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        finish();
        startActivity(intent);
    }

    /* 10/30 Button動畫 */
    public void btnAnimation(Button btnSet) {
        /* 10/10 載入按鈕動畫 */
        Animation btnAnim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.btn_bounce_anim);
        /* 10/10 設定按鈕的動畫 */
        btnSet.setAnimation(btnAnim);
        /* 10/10 設定反彈幅度0.05, 頻率8 */
        BounceInterpolator interpolator = new BounceInterpolator(0.05, 8);
        /* 10/10 套用反彈效果 */
        btnAnim.setInterpolator(interpolator);
        /* 10/10 運行按鈕動畫 */
        btnSet.startAnimation(btnAnim);
    }
}
