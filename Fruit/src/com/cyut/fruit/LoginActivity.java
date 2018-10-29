package com.cyut.fruit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.cyut.fruit.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText edt_email;
    private EditText edt_password;
    private TextView textViewSingup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private  LinearLayout linearLayout;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference emailRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        edt_email = (EditText) findViewById(R.id.editTextEmail);
        edt_password = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSingup = (TextView) findViewById(R.id.textViewSignup);
        linearLayout = (LinearLayout) findViewById(R.id.login_bk);

        buttonSignIn.setOnClickListener(this);
        textViewSingup.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        edt_email.setOnClickListener(this);

    }





    private void userinput(){
        edt_email.setTextColor(Color.parseColor(""));
    }

    private void userLogin(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function exection further
            return;
        }


        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        if (view == buttonSignIn){
            userLogin();
        }

        if (view == edt_email){

        }

        if (view == textViewSingup){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
