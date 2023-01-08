package com.example.expensetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    ImageButton ImgIncome,ImgExpences,ImgTransfer,menuBtnHome;
    TextView incomeTv,expenceTv,PreviesBalTv,TotalTv;
    FirebaseFirestore db;
    Button btnincome,btnexpense,btntransfer;
    CollectionReference CoUidReference;
    FirebaseAuth fauth;
    String currentUserLogin;
    int Income = 0;
    int Expense = 0;
    //IncomeAdapter incomeAdapter;
    MainActivity binding;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImgIncome = findViewById(R.id.btnImg_income);
        menuBtnHome = findViewById(R.id.menuBtn);
        ImgExpences = findViewById(R.id.btnImg_expence);
        ImgTransfer = findViewById(R.id.btnImg_transfer);
        incomeTv = findViewById(R.id.tvIncome);
        expenceTv = findViewById(R.id.tvExpense);
        //PreviesBalTv = findViewById(R.id.tv_privious_bal);
        TotalTv = findViewById(R.id.tvCurrentBalance);
        btnincome = findViewById(R.id.btnIncomeHistory);
        btnexpense = findViewById(R.id.btnExpenseHistory);
        btntransfer = findViewById(R.id.btnTransferHistory);

        menuBtnHome.setOnClickListener((v) -> showMenus());
        ImgIncome.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, IncomeActivity.class));
            finish();
        });

        ImgExpences.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ExpenceActivity.class));
            finish();
        });
        ImgTransfer.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,Transferactivity.class));
        });

        btnincome.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,IncomeHistory.class));
            finish();
        });

        btnexpense.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,ExpenseHistory.class));
            finish();
        });

        btntransfer.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,TransferHistory.class));
        });


        //ImgTransfer.setOnClickListener((v) -> fetchData());//startActivity(new Intent(MainActivity.this,TransferActivity.class)));
        db = FirebaseFirestore.getInstance();
        fauth =  FirebaseAuth.getInstance();
        currentUserLogin = fauth.getCurrentUser().getUid();
        //DoUidRef = db.document("Income");
        //CoUidReference = db.collection("4ewDlqZlDmXBUnp68YLHvRSk0742/Expense/4ewDlqZlDmXBUnp68YLHvRSk0742");
        //CoUidReference = db.collection("4ewDlqZlDmXBUnp68YLHvRSk0742/Expense/4ewDlqZlDmXBUnp68YLHvRSk0742");
//        DoUidRef = db.document("4ewDlqZlDmXBUnp68YLHvRSk0742/Income");
//        CoUidReference = db.collection("4ewDlqZlDmXBUnp68YLHvRSk0742");
//        DoUidRef = db.document("Income/4ewDlqZlDmXBUnp68YLHvRSk0742");
        totalOfIE();
        fetchIncomeData();
        fetchExpenseData();
        //fetchincomedetails();
//        incomeAdapter = new IncomeAdapter(this);
//        binding.
//        displayincome();

    }



//    private void displayincome()
//    {
//        FirebaseFirestore.getInstance().collection(currentUserLogin+"/Income/"+currentUserLogin).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
//
//                        for (DocumentSnapshot ds : dslist)
//                        {
//                            Income income1 = ds.toObject(com.example.expensetracker.Income.class);
//                        }
//
//                    }
//                });
//    }


    private void showMenus() {
        PopupMenu pm = new PopupMenu(MainActivity.this,menuBtnHome);
        pm.getMenu().add("Groups");
        pm.getMenu().add("Settings");
        pm.getMenu().add("Logout");
        pm.show();
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle() == "Logout") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,SignInActivity.class));
                    Utility.showToast(MainActivity.this,"Logout Successfully");
                    finish();
                    return true;
                }else if(menuItem.getTitle() == "Groups"){
                    startActivity(new Intent(MainActivity.this,CreateGroupActivity.class));
                    Utility.showToast(MainActivity.this,"Create Group");
                    finish();
                    return true;

                }
                else if (menuItem.getTitle() == "Settings")
                {
                    startActivity(new Intent(MainActivity.this,SettingActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void totalOfIE() {
        int total = Income - Expense;
        System.out.println("Total here "+total);
        if(total >= 0) {
            TotalTv.setText(String.valueOf(total));
            TotalTv.setTextColor(Color.parseColor("#27AE60"));
        }else{
            TotalTv.setText(String.valueOf(total));
            TotalTv.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    private void fetchIncomeData() {
        String stemp = currentUserLogin+"/Income/"+currentUserLogin;
        //CoUidReference = db.collection("4ewDlqZlDmXBUnp68YLHvRSk0742/Income/4ewDlqZlDmXBUnp68YLHvRSk0742");
        CoUidReference = db.collection(stemp);
        //System.out.printf("fetch"+stemp);
        CoUidReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Income in = documentSnapshot.toObject(Income.class);
                    int temp = in.getIncome();
                    Income += temp;
                    System.out.println(in);
                }
                incomeTv.setText(String.valueOf(Income));
                totalOfIE();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void fetchExpenseData() {
        String stemp = currentUserLogin+"/Expense/"+currentUserLogin;
        //CoUidReference = db.collection("4ewDlqZlDmXBUnp68YLHvRSk0742/Expense/4ewDlqZlDmXBUnp68YLHvRSk0742");
        CoUidReference = db.collection(stemp);
        CoUidReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Expence in = documentSnapshot.toObject(Expence.class);
                    int temp = in.getExpense();
                    Expense += temp;
                }
                System.out.println("/n");
                expenceTv.setText(String.valueOf(Expense));
                totalOfIE();
                //System.out.println("fetch "+data);
            }
        });

    }
}

/*
String EuId ="";
        //FirebaseUser cuser = FirebaseAuth.getInstance().getCurrentUser();
        EuId = fauth.getCurrentUser().getUid();
        DocumentReference reference = dbroot.collection(EuId).document("Income").collection(EuId).document("v9fARew3Uf908cG7w53Y");
        FirebaseFirestore path = dbroot.collection(EuId).document("Income").collection(EuId).getFirestore();

        //DocumentReference reference =   dbroot.collection(EuId).document("Income");//collection(cuser.getUid()).document();
        System.out.println("reference : "+reference.getId());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    incomeTv.setText(documentSnapshot.getLong("income").toString());
                    System.out.println("income : "+path);
                }else{
                    Utility.showToast(MainActivity.this,"Data Not found");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utility.showToast(MainActivity.this,"Failed to fetch data");
            }
        });
 */