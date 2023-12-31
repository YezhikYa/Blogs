package com.yezhik_ya.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.model.BlogPosts;
import com.yezhik_ya.repository.BlogsRepository;

public class BlogsViewModel extends ViewModel
{
    private BlogsRepository repository;
    private MutableLiveData<Boolean> successOperation;
    private MutableLiveData<BlogPosts> blogPostsLiveData;

    public BlogsViewModel(Context context)
    {
        blogPostsLiveData = new MutableLiveData<>();
        successOperation = new MutableLiveData<>();
        repository = new BlogsRepository(context);
    }
    public void add(BlogPost blogPost){
        repository.add(blogPost)
                .addOnSuccessListener(aBoolean ->
                {successOperation.setValue(true);})
                .addOnFailureListener(e ->
                {successOperation.setValue(false);});
    }
    public LiveData<Boolean> getSuccessOperation() { return successOperation; }
    public LiveData<BlogPosts> getAll(){
        blogPostsLiveData = repository.getAll();
        return blogPostsLiveData;
    }
}
