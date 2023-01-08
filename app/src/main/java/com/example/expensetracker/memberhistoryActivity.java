 package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 public class memberhistoryActivity extends AppCompatActivity {
    TextView memberData;
    ListView incomeList,ExpensesList;
     String uKey,cGrpId,userPro;
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference rootRef;
     Map<String,String> iMap = new HashMap<>();
     Map<String,String> eMap = new HashMap<>();
     int icount=1,ecount=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_history);

        memberData = findViewById(R.id.memDet);
        incomeList = findViewById(R.id.iList);
        ExpensesList = findViewById(R.id.elist);


        uKey = getIntent().getExtras().get("UserKey").toString();
        cGrpId = getIntent().getExtras().get("currentGrpId").toString();
        userPro = getIntent().getExtras().get("UserProfile").toString();
        memberData.setText(userPro);

        rootRef = database.getReference().child("GroupInfo").child(cGrpId).child("Income").child(uKey);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    allDetails(ds.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rootRef = database.getReference().child("GroupInfo").child(cGrpId).child("Expenses").child(uKey);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    allDetailsExpenses(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

     private void allDetailsExpenses(String key) {
         rootRef = database.getReference().child("GroupInfo").child(cGrpId).child("Expenses").child(uKey);
         rootRef.child(key).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 MemberExpenses mEs = snapshot.getValue(MemberExpenses.class);
                 eMap.put(String.valueOf(icount),("Expense : "+mEs.getMemberExpenses()+"\nDescription : "+mEs.getMemberDes()+"\nDate : "+mEs.getMemberDate()));
                 //System.out.println(snapshot);
                 addInAdapter(eMap,1);
                 icount++;
             }
             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

     }

     private void allDetails(String key) {
         rootRef = database.getReference().child("GroupInfo").child(cGrpId).child("Income").child(uKey);
         rootRef.child(key).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 MemberIncome mIn = snapshot.getValue(MemberIncome.class);
                 iMap.put(String.valueOf(ecount),("Income : "+mIn.getMemberIncome()+"\nDescription : "+mIn.getMemberDes()+"\nDate : "+mIn.getMemberDate()));
                 addInAdapter(iMap,0);
                 ecount++;
             }
             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

    }

     private void addInAdapter(Map<String,String> map,int i) {
            if (i == 0){
                MemberHistoryAdapter mha = new MemberHistoryAdapter(map);
                incomeList.setAdapter(mha);
            }else if(i == 1){
                MemberHistoryAdapter mha = new MemberHistoryAdapter(map);
                ExpensesList.setAdapter(mha);
            }
     }

 }