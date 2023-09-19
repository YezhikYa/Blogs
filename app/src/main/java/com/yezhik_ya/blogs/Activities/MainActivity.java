package com.yezhik_ya.blogs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.yezhik_ya.blogs.R;

public class MainActivity extends BaseActivity
{
    private Button btnAddPost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initializeViews()
    {
        btnAddPost = findViewById(R.id.btnAddPost);

        setListeners();
    }

    @Override
    protected void setListeners()
    {
        //Listener to btnAddPost
    }

}