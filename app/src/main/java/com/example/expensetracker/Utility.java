package com.example.expensetracker;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {
   static String EuId ="";
    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser cuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth fauth =  FirebaseAuth.getInstance();
        EuId = fauth.getCurrentUser().getUid();
        return FirebaseFirestore.getInstance().collection(EuId)
                .document("Expense").collection(cuser.getUid());
    }

    public static CollectionReference getCollectionReferenceIncomeForNotes() {
        FirebaseUser cuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth fauth =  FirebaseAuth.getInstance();
        EuId = fauth.getCurrentUser().getUid();
        return FirebaseFirestore.getInstance().collection(EuId)
                .document("Income").collection(cuser.getUid());
    }

    public static CollectionReference getCollectionReferenceTransferAmount() {
        FirebaseUser cuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth fauth =  FirebaseAuth.getInstance();
        EuId = fauth.getCurrentUser().getUid();
        return FirebaseFirestore.getInstance().collection(EuId)
                .document("Transfer").collection(cuser.getUid());
    }
}
