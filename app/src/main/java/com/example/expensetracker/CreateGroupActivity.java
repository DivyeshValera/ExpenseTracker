package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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

public class CreateGroupActivity extends AppCompatActivity {
    Button newGrpBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference RootRef;
    ImageButton bHomeBtn;
    ListView grpLv;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String currentUserId = fAuth.getInstance().getUid();
    //currentUserName = fAuth.getCurrentUser().getEmail();


    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfGroup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        newGrpBtn = findViewById(R.id.newGroupBtn);
        grpLv = findViewById(R.id.groupListView);
        bHomeBtn = findViewById(R.id.backHomeBtn);

        bHomeBtn.setOnClickListener(view -> {
            startActivity(new Intent(CreateGroupActivity.this, MainActivity.class));
            finish();
        });

        newGrpBtn.setOnClickListener((v) -> RequestNewGroup());
        IntializeFields();
        RetriveAndDisplayGrp();

        grpLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentName = adapterView.getItemAtPosition(i).toString();
                Intent groupMembersLs = new Intent(CreateGroupActivity.this, GroupMemberActivity.class);
                groupMembersLs.putExtra("gn", currentName);
                startActivity(groupMembersLs);
                finish();
            }
        });
    }


    private void IntializeFields() {
        RootRef = database.getReference("NumberOfGroup").child("Group").child(currentUserId);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfGroup);
        grpLv.setAdapter(arrayAdapter);
    }

    private void RetriveAndDisplayGrp() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                listOfGroup.clear();
                listOfGroup.addAll(set);
                // System.out.println("name "+set);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RequestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroupActivity.this);
        builder.setTitle("Enter Group Name :");
        final EditText groupNameField = new EditText(CreateGroupActivity.this);
        groupNameField.setHint("e.g. Family Expense");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = groupNameField.getText().toString();
                if (groupName == null || groupName.isEmpty()) {
                    Utility.showToast(CreateGroupActivity.this, "Empty Field Not allows");
                } else {
                    CreateNewGroupFire(groupName);
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

    // currentUserId = fAuth.getInstance().getUid();
    private void CreateNewGroupFire(String groupName) {
        RootRef = database.getReference("NumberOfGroup").child("Group")
                .child(currentUserId).child(groupName);
        RootRef.setValue("").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utility.showToast(CreateGroupActivity.this, groupName + " Created Successfully");
                        }
                    }
                });
    }
}