package com.yezhik_ya.blogs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yezhik_ya.blogs.Adapters.PostAdapter;
import com.yezhik_ya.blogs.R;
import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.model.BlogPosts;
import com.yezhik_ya.viewmodel.BlogsViewModel;

public class PostsActivity extends BaseActivity
{
    private RecyclerView rv;
    private FloatingActionButton fabAdd;
    private BlogPosts blogPosts;
    private PostAdapter adapter;
    private BlogsViewModel blogsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initializeViews();
        setRecyclerView();
    }

    private void setRecyclerView()
    {
        PostAdapter.OnItemClickListener listener = new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(BlogPost blogPost) {
                //oldBlogPost = blogPost;
                Intent update = new Intent(PostsActivity.this, BlogPostActivity.class);
                update.putExtra("POST", blogPost);
                startActivityForResult(update, 2);
            }
        };

        PostAdapter.OnItemLongClickListener longListener = new PostAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(BlogPost blogPost) {
                if(blogPost != null) {
                    //deleteBlogPost(blogPost);
                    return true;
                }
                return false;
            }
        };

        adapter = new PostAdapter(this, R.layout.single_post, blogPosts, listener, longListener);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void initializeViews()
    {
        rv = findViewById(R.id.rv);
        fabAdd = findViewById(R.id.fabAdd);

        setListeners();
    }

    @Override
    protected void setListeners()
    {
        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), BlogPostActivity.class);
                startActivity(intent);
            }
        });
    }
}