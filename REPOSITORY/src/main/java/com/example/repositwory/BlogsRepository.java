package com.example.repositwory;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BlogsRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public BlogsRepository()
        {
        db = FirebaseFirestore.getInstance();
        collection = db.collection("Blogs");
    }
}
