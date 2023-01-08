package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    TextView SignUpTv;
    EditText siemail,sipass;
    ProgressBar siProbar;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SignUpTv = findViewById(R.id.signUpTv);
        siemail = findViewById(R.id.SiEmailET);
        sipass = findViewById(R.id.SiPassET);
        siProbar = findViewById(R.id.SIPro);
        signInBtn = findViewById(R.id.signInB);

        signInBtn.setOnClickListener(v -> signInAccMethod());
        SignUpTv.setOnClickListener((v) -> startActivity(new Intent(SignInActivity.this,CreateAccountActivity.class)));
    }

    private void signInAccMethod() {
        String sIemail = siemail.getText().toString();
        String sIpass = sipass.getText().toString();
        boolean isValidate = SiFormValidation(sIemail, sIpass);
        if (!isValidate){
            return;
        }else{
            SignInWithFire(sIemail,sIpass);
        }
    }

    private void SignInWithFire(String sIemail, String sIpass) {
        SiChangeProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(sIemail,sIpass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                SiChangeProgress(false);
                if (task.isSuccessful()){
                    //successfully signIn
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(SignInActivity.this,MainActivity.class));
                        finish();
                    }else{
                        Utility.showToast(SignInActivity.this,"Email not verify,please verify your mail ");
                    }
                }else{
                    //Failed signIn
                    Utility.showToast(SignInActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void SiChangeProgress(boolean inProgress){
        if(inProgress){
            siProbar.setVisibility(View.VISIBLE);
            signInBtn.setVisibility(View.GONE);
        }else{
            siProbar.setVisibility(View.GONE);
            signInBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean SiFormValidation(String semail, String spass){
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            siemail.setError("Invalid Email Id");
            return false;
        }else if(spass.length() < 6){
            sipass.setError("Password length should be grater than 6");
            return false;
        }

        return true;
    }
}