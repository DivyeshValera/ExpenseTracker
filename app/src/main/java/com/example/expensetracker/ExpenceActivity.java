package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenceActivity extends AppCompatActivity {
    EditText ExpenseEt,ExpenseDesEt,ExpenseDate;
    Button addExpenseBtn;
    ImageButton backToHomE;
    FirebaseFirestore Efirestore;
    FirebaseAuth Efauth;
    Calendar calendar;
     String expenseDatePick;
     int e =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);
        calendar = Calendar.getInstance();
        ExpenseEt = findViewById(R.id.ExpenseAdd);
        ExpenseDesEt = findViewById(R.id.ExpenseDes);
        backToHomE = findViewById(R.id.backExpense);
        addExpenseBtn = findViewById(R.id.Expensebutton);
        ExpenseEt.setText(String.valueOf(e));
        ExpenseDate = findViewById(R.id.expenseDate);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        ExpenseDate.setText(date_n);
        Efauth = FirebaseAuth.getInstance();
        Efirestore = FirebaseFirestore.getInstance();

        //ExpenseDate.setText(mDay + ","+(mMonth+1)+" "+mYear);
        ExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp1 = new DatePickerDialog(ExpenceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        ExpenseDate.setText((MONTHS[i1])+" "+i2+", "+i);
                        expenseDatePick = (MONTHS[i1])+" "+i2+", "+i;
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dp1.show();
            }
        });
        addExpenseBtn.setOnClickListener((v) -> addExpenceMethod());
        backToHomE.setOnClickListener(view -> {
            startActivity(new Intent(ExpenceActivity.this,MainActivity.class));
            finish();
        });

    }

    private void addExpenceMethod() {
        try {
            int SExpence = Integer.parseInt(ExpenseEt.getText().toString());
            String SExpencedec = ExpenseDesEt.getText().toString();

            if (SExpence < 1) {
                ExpenseEt.setError("amount should be greater than zero");
                return;
            } else if (SExpencedec.isEmpty() || SExpencedec == null) {
                ExpenseDesEt.setError("Empty filled not allowed");
                return;
            } else {
                Expence ie = new Expence();
                ie.setExpense(SExpence);
                ie.setDescription(SExpencedec);
                ie.setDate(expenseDatePick);
                AddExpenceINFireStore(ie);
            }
        }catch (NumberFormatException ex){
            ExpenseEt.setError("Empty field not allowed");
        }
    }

    private void AddExpenceINFireStore(Expence ie) {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();
        documentReference.set(ie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Utility.showToast(ExpenceActivity.this,"Expense Adds Successfully");
                    finish();
                    startActivity(new Intent(ExpenceActivity.this, MainActivity.class));
                }else{
                    Utility.showToast(ExpenceActivity.this,"Filed while adding Expense");
                }
            }
        });



//        EuId = Efauth.getCurrentUser().getUid();
//        DocumentReference documentReference = Efirestore.collection("Expense").document(EuId);
//        Map<String,Object> incomeData = new HashMap<>();
//        incomeData.put("Expense",SExpence);
//        incomeData.put("Description",sEdec);
//        documentReference.set(incomeData).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Utility.showToast(ExpenceActivity.this,"Income added successfully");
//                Log.d("OnSuccess Log: ",EuId);
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Utility.showToast(ExpenceActivity.this,e.toString());
//                System.out.println("OnSuccess Log: "+EuId);
//                finish();
//            }
//        });
    }
}