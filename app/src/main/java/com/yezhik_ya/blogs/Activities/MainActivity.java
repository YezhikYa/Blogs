package com.yezhik_ya.blogs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(MainActivity.this, BlogPostActivity.class)); }
        });
    }

}