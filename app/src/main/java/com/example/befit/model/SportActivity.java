package com.example.befit.model;

import androidx.room.PrimaryKey;
import java.io.Serializable;

public class SportActivity  implements Serializable {
    private String imageUrl;
    private String name;

    private String desc;

    private int duration, reps, sets;
    public SportActivity(String imageUrl, String name,String desc, int duration, int reps, int sets) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.desc = desc;
        this.duration = duration;
        this.reps = reps;
        this.sets = sets;
    }

    public void setImageUrl(String imageId) {this.imageUrl = imageId;}
    public void setName(String name) {this.name = name;}

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getName() {return name;}

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDuration() {
        return duration;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public String getDesc() {
        return desc;
    }
}
