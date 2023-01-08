package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingActivity extends AppCompatActivity {

    Button logout;
    EditText mail;
    ImageButton backbutton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference RootRef;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String currentUserId = fAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mail = findViewById(R.id.edemail);
        logout = findViewById(R.id.btnlogout);
        backbutton = findViewById(R.id.backSetting);
        logout.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this,SignInActivity.class  ));
            Utility.showToast(SettingActivity.this,("Logout Successfully"));
        });
        backbutton.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this,MainActivity.class));
            finish();
        });
        email();
    }
    private void email()
    {
        mail.setText(currentUserId);
    }
}