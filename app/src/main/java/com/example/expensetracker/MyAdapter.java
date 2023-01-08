package com.example.expensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyAdapter extends BaseAdapter {
    private final ArrayList mData;
    Context context;
    Map.Entry<String,String> item;
    ArrayList<String> id = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef;
    public MyAdapter(Map<String, String> map, GroupMemberActivity groupMemberActivity, String grpId) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        context = groupMemberActivity;
        clickGId = grpId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String,String> getItem(int i) {
        return (Map.Entry<String, String>) mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View result;
        if (convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlistlayout,parent,false);
        }else{
            result = convertView;
        }
      item  = getItem(i);
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());
        id.add(item.getValue());
        Button IncomeBtn = result.findViewById(R.id.button);
        Button ExpensesBtn = result.findViewById(R.id.button1);
        Button ShowHistory = result.findViewById(R.id.button2);
        IncomeBtn.setOnClickListener(v -> {storeDataFireAlert(i,0);

        });
        ExpensesBtn.setOnClickListener(v -> {storeDataFireAlert(i,1);
        });
        ShowHistory.setOnClickListener(v -> {

            showDataOnFire( i);

        });
        return result;
    }
    private void storeDataFireAlert(int i,int btnId) {
        AlertDialog.Builder  builder= new AlertDialog.Builder(context);
        if (btnId == 0){
            builder.setTitle("Add Income");

        }else if (btnId==1){
            builder.setTitle("Add Expenses");

        }

        final EditText edNum = new EditText(context);
        final EditText des = new EditText(context);
        final EditText Idate = new EditText(context);

        edNum.setHint("Enter Digit");
        edNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        des.setHint("Enter description");
        Idate.setHint("Enter Date");

        LinearLayout l1 = new LinearLayout(context);
        l1.setOrientation(LinearLayout.VERTICAL);
        l1.addView(edNum);
        l1.addView(des);
        l1.addView(Idate);
        builder.setView(l1);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    addingDataOnFire(edNum.getText().toString(),des.getText().toString(),Idate.getText().toString(),i,btnId);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    String clickGId;

    private void addingDataOnFire(String number, String descreption, String dateIncome, int i,int btnId) {
        rootRef = database.getReference().child("GroupInfo").child(clickGId);
        rootRef.child("memberList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(btnId == 0){

                    for (DataSnapshot ds : snapshot.getChildren()){
                        //System.out.println(ds);
                        if(id.get(i).equals(ds.getValue().toString())){
                            addSingleUserValue(i,ds.getKey(),number,descreption,dateIncome);
                        }

                    }
                }else if(btnId == 1) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        //System.out.println(ds);
                        if(id.get(i).equals(ds.getValue().toString())){
                            addSingleUserExpensesValue(i,ds.getKey(),number,descreption,dateIncome);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showDataOnFire(int i) {
        Intent intent = new Intent(context,memberhistoryActivity.class);
        rootRef = database.getReference().child("GroupInfo").child(clickGId);
        rootRef.child("memberList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    //System.out.println(ds);
                    if(id.get(i).equals(ds.getValue().toString())){
                            intent.putExtra("currentGrpId",clickGId);
                            intent.putExtra("UserKey",ds.getKey());
                        intent.putExtra("UserProfile",ds.getValue().toString());
                        context.startActivity(intent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utility.showToast(context,"Data Not Found");
            }
        });
    }
    private void addSingleUserExpensesValue(int i, String key, String number, String descreption, String dateExpenses) {
        Map<String,Object> map =  new HashMap<>();
        Random random = new Random();
        MemberExpenses memExpensesClass = new MemberExpenses();
        int incomeIdRam = random.nextInt(1000);
        memExpensesClass.setMemberExpenses(number);
        memExpensesClass.setMemberDes(descreption);
        memExpensesClass.setMemberDate(dateExpenses);
        rootRef = database.getReference().child("GroupInfo").child(clickGId).child("Expenses").child(key);
        map.put(String.valueOf(incomeIdRam),memExpensesClass);
        rootRef.updateChildren(map);
        Utility.showToast(context,"Expense Added Successfully");
    }

    private void addSingleUserValue(int i, String kMid, String number, String descreption,String dateI) {
        System.out.println(i+kMid+number+descreption+dateI);
        Map<String,Object> map =  new HashMap<>();
        Random random = new Random();
        MemberIncome memIncomeClass = new MemberIncome();
        int incomeIdRam = random.nextInt(1000);
        memIncomeClass.setMemberIncome(number);
        memIncomeClass.setMemberDes(descreption);
        memIncomeClass.setMemberDate(dateI);
        rootRef = database.getReference().child("GroupInfo").child(clickGId).child("Income").child(kMid);
        map.put(String.valueOf(incomeIdRam),memIncomeClass);
       rootRef.updateChildren(map);
       Utility.showToast(context,"Income Added Successfully");
        //System.out.println(incomeIdRam+":"+map);
    }

}
