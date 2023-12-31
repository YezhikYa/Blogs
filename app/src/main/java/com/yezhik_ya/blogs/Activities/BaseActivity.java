package com.yezhik_ya.blogs.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yezhik_ya.blogs.R;

public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected abstract void initializeViews();
    protected abstract void setListeners();
}