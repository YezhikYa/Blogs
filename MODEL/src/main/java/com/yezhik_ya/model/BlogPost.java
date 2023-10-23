package com.yezhik_ya.model;

import java.io.Serializable;
import java.util.Objects;

public class BlogPost extends BaseEntity implements Serializable
{
    private String author;
    private String title;
    private String content;
    private Long date;
    public BlogPost() {};

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getDate() { return date; }
    public void setDate(Long date) { this.date = date; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return Objects.equals(author, blogPost.author) && Objects.equals(title, blogPost.title) && Objects.equals(content, blogPost.content) && Objects.equals(date, blogPost.date);
    }
}
