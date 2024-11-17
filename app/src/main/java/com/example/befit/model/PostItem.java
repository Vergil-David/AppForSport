package com.example.befit.model;

import java.io.Serializable;

public class PostItem implements Serializable {
    private String title;
    private String description;
    private String imageUrl;
    private String postDate;


    public PostItem(String title, String description, String imageUrl, String postDate)
    {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.postDate = postDate;
    }

    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getImageUrl() {return imageUrl;}
    public String getPostDate() {return postDate;}

    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
    public void setPostDate(String postDate) {this.postDate = postDate;}
}
