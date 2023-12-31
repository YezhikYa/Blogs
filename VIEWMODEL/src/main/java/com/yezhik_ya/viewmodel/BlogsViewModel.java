package com.yezhik_ya.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.repositwory.BlogsRepository;
import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.model.BlogPosts;

public class BlogsViewModel extends ViewModel
{
    private MutableLiveData<Boolean> successOperation;
    private BlogsRepository repository;
    private MutableLiveData<BlogPosts> blogsPostsLiveData;

    public BlogsViewModel() { successOperation = new MutableLiveData<>(); }
    public BlogsViewModel(Context context){
        successOperation = new MutableLiveData<>();
        repository = new BlogsRepository(context);
        blogsPostsLiveData = new MutableLiveData<>();
    }


    public void add(BlogPost blogPosts) {
        repository.add(blogPost)
                .addOnSuccessListener(aBoolean -> {successOperation.setValue(true);})
                .addOnFailureListener(e ->{successOperation.setValue(false);});
    }

    public LiveData<Boolean> getSuccessOperation() { return successOperation; }

}
