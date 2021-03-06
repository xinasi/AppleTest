package com.cyut.fruit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        textViewSignin.setPaintFlags(textViewSignin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            editTextEmail.setError("請輸入Email");
            //Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function exection further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            editTextEmail.setError("請輸入Password");
            //Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validations are OK
        //we will first show a progressbar
        progressDialog.setMessage("Registering Please wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    email
                            );

                            FirebaseDatabase.getInstance().getReference("Data/User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        //display a failure message
                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_lose), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.input_error_email_invalid), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            btnAnimation(buttonRegister);
            Handler btnRegisterHandler = new Handler();
            btnRegisterHandler.postDelayed(btnRegisterDelay, 450);
        }
        if (view == textViewSignin){
            //will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    /* 運行完動畫執行註冊 */
    private Runnable btnRegisterDelay = new Runnable() {
        public void run() {
            registerUser();
        }
    };

    /* 10/30 Button動畫 */
    public void btnAnimation(Button btnSet) {
        /* 10/10 載入按鈕動畫 */
        Animation btnAnim = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.btn_bounce_anim);
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
