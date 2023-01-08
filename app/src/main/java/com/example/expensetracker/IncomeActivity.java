package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IncomeActivity extends AppCompatActivity {
    EditText incomeEt,DesEt,DateEt;
    Button addIncomeBtn;
    ImageButton backToHomI;
    FirebaseFirestore firestore;
    FirebaseAuth fauth;
    Calendar cal;
    static String IncomeDatePick;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        cal = Calendar.getInstance();
        incomeEt = findViewById(R.id.incomeAdd);
        incomeEt.setText(String.valueOf(i));
        DesEt = findViewById(R.id.incomeDes);
        addIncomeBtn = findViewById(R.id.Incomebutton);
        backToHomI = findViewById(R.id.backIncome);
        DateEt = findViewById(R.id.IncomeDate);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        DateEt.setText(date_n);
        fauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        DateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(IncomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        DateEt.setText((MONTHS[i1])+" "+i2+", "+i);
                        IncomeDatePick = (MONTHS[i1])+" "+i2+", "+i;
                    }
                },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });
        addIncomeBtn.setOnClickListener((v) -> addIncomeMethod());
        backToHomI.setOnClickListener(view -> {
            startActivity(new Intent(IncomeActivity.this,MainActivity.class));
            finish();
        });

    }

    private void addIncomeMethod() {
        try{
            int Sincome = Integer.parseInt(incomeEt.getText().toString());
            String Sdec = DesEt.getText().toString();
            if(Sincome < 1){
                incomeEt.setError("Amount should be greater than zero");
                return;
            }else if(Sdec.isEmpty() || Sdec == null){
                DesEt.setError("Empty filled not allowed");
                return;
            }else {
                Income income = new Income();
                income.setIncome(Sincome);
                income.setDate(IncomeDatePick);
                income.setDescription(Sdec);
                AddIncomeINFireStore(income);
            }
        }catch(NumberFormatException ex){
            incomeEt.setError("Empty field not allowed");
            //System.out.println("Exception" + ex);
        }

    }

    private void AddIncomeINFireStore(Income income) {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceIncomeForNotes().document();
        documentReference.set(income).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(IncomeActivity.this,"Income Adds Successfully");
                    finish();
                    startActivity(new Intent(IncomeActivity.this, MainActivity.class));
                }else{
                    Utility.showToast(IncomeActivity.this,"Filed while adding  Income");
                }
            }
        });
    }
}