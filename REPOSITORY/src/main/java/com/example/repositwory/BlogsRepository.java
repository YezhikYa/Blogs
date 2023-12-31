package com.example.repositwory;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BlogsRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public BlogsRepository() {
        db = FirebaseFirestore.getInstance();
        collection = db.collection("Blogs");
    }

    public BlogsRepository(Context context) {
        try {
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            FirebaseInstance instance =
                    FirebaseInstance.instance(context);

            db = FirebaseFirestore
                    .getInstance(FirebaseInstance.app);
        }

        collection = db.collection("Blogs");

    }
}
