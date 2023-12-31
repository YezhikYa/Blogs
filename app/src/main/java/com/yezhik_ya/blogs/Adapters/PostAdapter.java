package com.yezhik_ya.blogs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yezhik_ya.blogs.R;
import com.yezhik_ya.helper.DateUtil;
import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.model.BlogPosts;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private Context context;
    private int blog_layout;
    private BlogPosts blogPosts;

    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public PostAdapter(Context context, int blog_layout, BlogPosts blogPosts, OnItemClickListener listener, OnItemLongClickListener longListner) {
        this.context = context;
        this.blog_layout = blog_layout;
        this.blogPosts = blogPosts;
        this.listener = listener;
        this.longListener = longListner;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(context).inflate(blog_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        BlogPost blogPost = blogPosts.get(position);

        if(blogPost != null) {
            holder.bind(blogPost, listener, longListener);
        }
    }

    @Override
    public int getItemCount() {
        return (blogPosts != null) ? blogPosts.size() : 0;
    }

    public static class PostHolder extends RecyclerView.ViewHolder {

        public TextView tvAuthor;
        public TextView tvTitle;
        public TextView tvDate;
        public TextView tvContent;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.etAuthor);
            tvTitle = itemView.findViewById(R.id.etTitle);
            tvDate = itemView.findViewById(R.id.etDate);
            tvContent = itemView.findViewById(R.id.etContent);
        }

        public void bind(BlogPost blogPost, OnItemClickListener listener, OnItemLongClickListener longListener) {

            tvAuthor.setText(blogPost.getAuthor());
            tvTitle.setText(blogPost.getTitle());
            tvDate.setText(DateUtil.longDateToString(blogPost.getDate()));
            tvContent.setText(blogPost.getContent());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(blogPost);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onItemLongClicked(blogPost);
                    return true;
                }
            });

        }

    }

    public void setBlogPosts(BlogPosts blogPosts) {
        this.blogPosts = blogPosts;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClicked(BlogPost blogPost);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(BlogPost blogPost);
    }

}

