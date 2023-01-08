package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExpenseHistory extends AppCompatActivity {

    ListView listView;
    ImageButton back;
    ArrayList<String> expensearrayList;
    ArrayAdapter<String> arrayAdapter;

    CollectionReference CoUidReference;
    FirebaseAuth fauth;
    FirebaseFirestore db;
    String currentUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_history);

        listView = findViewById(R.id.listviewexpense);
        back = findViewById(R.id.backbtn);
        expensearrayList = new ArrayList<>();

        showexpense();

        back.setOnClickListener(view -> {
            startActivity(new Intent(ExpenseHistory.this,MainActivity.class));
            finish();
        });

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,expensearrayList);
        listView.setAdapter(arrayAdapter);


    }

    private void showexpense()
    {
        db = FirebaseFirestore.getInstance();
        fauth =  FirebaseAuth.getInstance();
        currentUserLogin = fauth.getCurrentUser().getUid();

        String stemp = currentUserLogin+"/Expense/"+currentUserLogin;
        CoUidReference = db.collection(stemp);
        CoUidReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Expence in = documentSnapshot.toObject(Expence.class);
                    expensearrayList.add(("Expense : "+in.getExpense() + ", Description : " + in.getDescription() + ", Date : "+in.getDate()));
                    System.out.println("Data"+in);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}