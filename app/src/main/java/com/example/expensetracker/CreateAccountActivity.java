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

public class CreateAccountActivity extends AppCompatActivity {
    TextView SuLoginTv;
    EditText UpEmail,UpPass,UpConfirmPass;
    ProgressBar UpProgressBar;
    Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        SuLoginTv = findViewById(R.id.signInTv);
        UpEmail = findViewById(R.id.emailET);
        UpPass = findViewById(R.id.passET);
        UpConfirmPass = findViewById(R.id.confirmpassET);
        UpProgressBar = findViewById(R.id.SuPro);
        signUpBtn = findViewById(R.id.signUpB);

        signUpBtn.setOnClickListener(v -> SignUpMethod());
        SuLoginTv.setOnClickListener((v) -> startActivity(new Intent(CreateAccountActivity.this,SignInActivity.class)));
    }

     void SignUpMethod() {
        String Semail = UpEmail.getText().toString();
        String Spass = UpPass.getText().toString();
        String SConfirmPass = UpConfirmPass.getText().toString();
         boolean isValidation = FormValidation(Semail, Spass, SConfirmPass);
         if(!isValidation){
             return;
         }else{
             SignUpFireBase(Semail,Spass);
         }
    }

     void SignUpFireBase(String semail, String spass) {
        ChangeProgress(true);
         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
         firebaseAuth.createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                    ChangeProgress(false);
                    if (task.isSuccessful()){
                        Utility.showToast(CreateAccountActivity.this,"successfully account created ,check Your mail to verify");
                        firebaseAuth.getCurrentUser().sendEmailVerification();
                        firebaseAuth.signOut();
                        finish();
                    }else{
                        Utility.showToast(CreateAccountActivity.this,task.getException().getLocalizedMessage());
                    }
             }
         });
    }
    void ChangeProgress(boolean inProgress){
        if(inProgress){
            UpProgressBar.setVisibility(View.VISIBLE);
            signUpBtn.setVisibility(View.GONE);
        }else{
            UpProgressBar.setVisibility(View.GONE);
            signUpBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean FormValidation(String semail, String spass, String SConfirmPass){
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            UpEmail.setError("Invalid Email Id");
            return false;
        }else if(spass.length() < 6){
            UpPass.setError("Password length should be grater than 6");
            return false;
        }else if(!SConfirmPass.equals(spass)){
            UpConfirmPass.setError("Password not matched");
            return false;
        }
        
        return true;
    }
}