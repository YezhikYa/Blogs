package com.yezhik_ya.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yezhik_ya.model.BlogPost;

public class BlogsViewModel extends ViewModel
{
    private MutableLiveData<Boolean> successOperation;

    public BlogsViewModel() { successOperation = new MutableLiveData<>(); }

    public void add(BlogPost blogPost) { successOperation.setValue(true); }

    public LiveData<Boolean> getSuccessOperation() { return successOperation; }

}
