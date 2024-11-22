package com.example.befit.model;

import androidx.room.PrimaryKey;
import java.io.Serializable;

public class SportActivity  implements Serializable {
    private String imageUrl;
    private String name;

    private String desc;

    private int duration, sets;
    private double met;
    public SportActivity(String imageUrl, String name,String desc, int duration, int sets, double met) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.desc = desc;
        this.duration = duration;
        this.met = met;
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

    public void setMet(double met) {
        this.met = met;
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

    public double getMet() {
        return met;
    }

    public int getSets() {
        return sets;
    }

    public String getDesc() {
        return desc;
    }
}
