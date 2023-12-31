package com.yezhik_ya.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.model.BlogPosts;

public class BlogsRepository
{
    private final MutableLiveData<BlogPosts> blogsLiveData;
    private FirebaseFirestore db;
    private CollectionReference collection;

    public BlogsRepository(Context context)
    {
        blogsLiveData = new MutableLiveData<>();

        try
        {
            db = FirebaseFirestore.getInstance();
        }
        catch (Exception e)
        {
            FirebaseInstance instance = FirebaseInstance.instance(context);

            db = FirebaseFirestore.getInstance(FirebaseInstance.app);
        }

        collection = db.collection("Blogs");
    }
    public Task<Boolean> add (BlogPost blogPost) {

        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<Boolean>();
        DocumentReference document = collection.document();
        blogPost.setIdFs(document.getId());
        document.set(blogPost).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void unused) {
                        taskCompletion.setResult(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        taskCompletion.setResult(false);
                    }
                });

        return taskCompletion.getTask();
    }
    public MutableLiveData<BlogPosts> getAll()
    {
        BlogPosts blogPosts = new BlogPosts();
        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot querySnapshots)
            {
                if (querySnapshots != null && !querySnapshots.isEmpty())
                {
                    for (DocumentSnapshot document : querySnapshots)
                    {
                        BlogPost blogPost = document.toObject(BlogPost.class);
                        if (blogPost  != null)
                            blogPosts.add(blogPost);
                    }
                }
                blogsLiveData.postValue(blogPosts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                blogsLiveData.postValue(blogPosts);
            }
        });

        return blogsLiveData;
    }
}
