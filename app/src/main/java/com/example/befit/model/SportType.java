package com.example.befit.model;

import java.io.Serializable;
import java.util.List;

public class SportType  implements Serializable {
    private String name;
    private int imageId;
    private int ImageBackGroudId;

    List<SportActivity> activities;

    public SportType(String name, int imageId, int ImageBackGroudId, List<SportActivity>activities) {
        this.name = name;
        this.imageId = imageId;
        this.ImageBackGroudId = ImageBackGroudId;
        this.activities = activities;
    }

    public void setName(String text) {this.name = text;}
    public void setImageId(int imageId) {this.imageId = imageId;}
    public void setImageBackGroudId(int ImageBackGroudId) {this.ImageBackGroudId = ImageBackGroudId;}

    public String getName() {return name;}
    public int getImageId() {return imageId;}
    public int getImageBackGroudId() {return ImageBackGroudId;}
    public List<SportActivity> getActivities() {return activities;}
}
