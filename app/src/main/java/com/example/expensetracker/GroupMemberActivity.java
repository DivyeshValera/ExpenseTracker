package com.example.expensetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GroupMemberActivity extends AppCompatActivity {
    ImageButton addMemberBtn,backBtn;
    TextView GpTitlePage,cui;
    ListView newMemberLv;
    private FirebaseAuth fAuth;
    private String GrpTitleName,currentUserId,currentUserName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference RootRef;

    private ArrayAdapter<String> newArrayAdapter;
    private ArrayList<String> newListOfGroup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        addMemberBtn = findViewById(R.id.AddMemBtn);
        backBtn = findViewById(R.id.BackGroupBtn);
        cui = findViewById(R.id.CurrentuserInfo);
        newMemberLv = findViewById(R.id.newMemListview);
        GrpTitleName = getIntent().getExtras().get("gn").toString();
        GpTitlePage = findViewById(R.id.GPTitle);
        GpTitlePage.setText(GrpTitleName);

        fAuth = FirebaseAuth.getInstance();
        currentUserId = fAuth.getInstance().getUid();
        currentUserName = fAuth.getCurrentUser().getEmail();
        RetriveAndDisplayGrpCurrent();
        IntializeFields();

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(GroupMemberActivity.this,CreateGroupActivity.class));
            finish();
        });


        RetriveAndDisplayGrp();
        addMemberBtn.setOnClickListener((v) -> addMemberFire());
    }

    private void IntializeFields() {
        RootRef = database.getReference("NumberOfGroup").child("Group")
                .child(currentUserId).child(GrpTitleName).child("Member");
        newArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, newListOfGroup);
        newMemberLv.setAdapter(newArrayAdapter);
    }

    private void RetriveAndDisplayGrpCurrent() {
        RootRef = database.getReference("NumberOfGroup").child("Group")
                .child(currentUserId).child(GrpTitleName).child("Owner");
        RootRef.child(currentUserId).setValue("");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                cui.setText(set.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RetriveAndDisplayGrp() {
        RootRef = database.getReference("NumberOfGroup").child("Group")
                .child(currentUserId).child(GrpTitleName).child("Member");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                newListOfGroup.clear();
                newListOfGroup.addAll(set);
                // System.out.println("name "+set);
                newArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addMemberFire() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupMemberActivity.this);
        builder.setTitle("Enter Member Name :");
        final EditText memberId = new EditText(GroupMemberActivity.this);
        memberId.setHint("Enter Member Id");
        builder.setView(memberId);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = memberId.getText().toString();
                if (groupName == null || groupName.isEmpty()) {
                    Utility.showToast(GroupMemberActivity.this, "Empty Field Not allows");
                } else {
                    AddNewMemberFire(groupName);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void AddNewMemberFire(String memId) {
        RootRef.child(memId).setValue("").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utility.showToast(GroupMemberActivity.this, memId + " Created Successfully");
                        }
                    }
                });
    }
}