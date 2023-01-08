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

public class TransferHistory extends AppCompatActivity {

    ListView listView;
    ImageButton imageButton;
    ArrayList<String> transferarrayList;

    CollectionReference CoUidReference;
    FirebaseAuth fauth;
    FirebaseFirestore db;
    String currentUserLogin;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);

        listView = findViewById(R.id.listviewtransfer);
        imageButton = findViewById(R.id.Btnback);

        transferarrayList = new ArrayList<>();
        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(TransferHistory.this,MainActivity.class));
            finish();
        });

        showHistory();


        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,transferarrayList);
        listView.setAdapter(arrayAdapter);


    }

    private void showHistory()
    {

        db = FirebaseFirestore.getInstance();
        fauth =  FirebaseAuth.getInstance();
        currentUserLogin = fauth.getCurrentUser().getUid();

        String stemp = currentUserLogin+"/Transfer/"+currentUserLogin;
        CoUidReference = db.collection(stemp);
        CoUidReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Transfer in = documentSnapshot.toObject(Transfer.class);
                    transferarrayList.add(("Amount : "+in.getTargetaccount() +", From User : " + in.getSourceaccount() + ", To User : "
                            + in.getTargetaccount() +  ", Description : " + in.getDescription() + ", Date : "+in.getDate()));
                    System.out.println("Data"+in.getTargetaccount()+in.getSourceaccount());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}