package com.example.befit.model;

import androidx.room.PrimaryKey;
import java.io.Serializable;

public class SportActivity  implements Serializable {
    private int imageId;
    private String name;

    public SportActivity(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public void setImagId(int imageId) {this.imageId = imageId;}
    public void setName(String name) {this.name = name;}

    public int getImageId() {return imageId;}
    public String getName() {return name;}
}
