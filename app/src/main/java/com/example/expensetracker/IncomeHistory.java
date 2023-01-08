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

public class IncomeHistory extends AppCompatActivity
{
    ListView listView;
    ImageButton imageButton;
    ArrayList<String> incomeArrayList;

    CollectionReference CoUidReference;
    FirebaseAuth fauth;
    FirebaseFirestore db;
    String currentUserLogin;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_history);
        listView = findViewById(R.id.listviewincome);
        imageButton = findViewById(R.id.backHomeBtn);


        incomeArrayList = new ArrayList<>();


        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(IncomeHistory.this,MainActivity.class));
            finish();
        });
        showincome();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,incomeArrayList);
        listView.setAdapter(arrayAdapter);
    }

    private void showincome() {
        db = FirebaseFirestore.getInstance();
        fauth =  FirebaseAuth.getInstance();
        currentUserLogin = fauth.getCurrentUser().getUid();

        String stemp = currentUserLogin+"/Income/"+currentUserLogin;
        CoUidReference = db.collection(stemp);
        CoUidReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Income in = documentSnapshot.toObject(Income.class);
                    incomeArrayList.add(("Income : "+in.getIncome() + ", Description : " + in.getDescription() + ", Date : "+in.getDate()));
                    //System.out.println("Data"+);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

}