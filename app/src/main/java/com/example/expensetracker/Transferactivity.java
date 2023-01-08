package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Transferactivity extends AppCompatActivity {

    ImageButton imageButton;
    EditText amount;
    EditText sourceaccount;
    EditText targetaccount;
    EditText description;
    EditText date;
    Button addIncomeBtn;
    FirebaseFirestore firestore;
    FirebaseAuth fauth;
    Calendar cal;
    static String IncomeDatePick;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferactivity);
        imageButton= findViewById(R.id.backTransfer);

        amount = findViewById(R.id.transferAmount);
        amount.setText(String.valueOf(i));
        sourceaccount = findViewById(R.id.transfersource);
        targetaccount = findViewById(R.id.transfertarget);
        description = findViewById(R.id.transferdesc);
        date = findViewById(R.id.transferDate);
        cal = Calendar.getInstance();
        addIncomeBtn = findViewById(R.id.transferAdd);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        date.setText(date_n);
        fauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        addIncomeBtn.setOnClickListener(view -> { addamount();});
        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(Transferactivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        date.setText((MONTHS[i1])+" "+i2+", "+i);
                        IncomeDatePick = (MONTHS[i1])+" "+i2+", "+i;
                    }
                },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });



    }

    private void addamount()
    {
        try
        {
            int tramount = Integer.parseInt(amount.getText().toString());
            String trdesc = description.getText().toString();
            String trsource = sourceaccount.getText().toString();
            String traccount = targetaccount.getText().toString();
            if(tramount < 1){
                amount.setError("Amount should be greater than zero");
                return;
            }else if(trdesc.isEmpty() || trdesc == null){
                description.setError("Empty filled not allowed");
                return;
            }
            else {
                Transfer tr = new Transfer();
                tr.setTransfer(tramount);
                tr.setDate(IncomeDatePick);
                tr.setDescription(trdesc);
                tr.setSourceaccount(trsource);
                tr.setTargetaccount(traccount);

                AddTransferINFireStore(tr);
            }
        }
        catch (NumberFormatException ex)
        {
            amount.setError("Empty field not allowed");
        }
    }

    private void AddTransferINFireStore(Transfer tr)
    {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceTransferAmount().document();
        documentReference.set(tr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(Transferactivity.this,"Transfer Successfully");
                    finish();
                    startActivity(new Intent(Transferactivity.this, MainActivity.class));
                }else{
                    Utility.showToast(Transferactivity.this,"Filed while adding  Transfer Amount");
                }
            }
        });
    }
}