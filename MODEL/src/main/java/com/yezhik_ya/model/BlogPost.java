package com.yezhik_ya.model;

import java.io.Serializable;

public class BlogPost extends BaseEntity implements Serializable
{
    private String author;
    private String title;
    private String content;
    private Long date;
    public BlogPost() {};


}
