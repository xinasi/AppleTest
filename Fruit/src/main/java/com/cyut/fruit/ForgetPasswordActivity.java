package com.cyut.fruit;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnResetPWD;
    private EditText edtEmailforResetPWD;
    private TextView txvToSignIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        txvToSignIn = (TextView) findViewById(R.id.txvToSignIn);
        txvToSignIn.setPaintFlags(txvToSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txvToSignIn.setOnClickListener(this);

        edtEmailforResetPWD = (EditText) findViewById(R.id.edt_EmailforResetPWD);
        btnResetPWD = (Button) findViewById(R.id.btn_resetPWD);
        btnResetPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = edtEmailforResetPWD.getText().toString();
                if (TextUtils.isEmpty(userEmail)) {
                    edtEmailforResetPWD.setError("請輸入e-mail");
                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPasswordActivity.this,"密碼重設信件已寄出，請確認您的e-mail信箱",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(ForgetPasswordActivity.this,"錯誤" + message,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

    public void onClick(View view) {

        if (view == txvToSignIn){
            //will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
