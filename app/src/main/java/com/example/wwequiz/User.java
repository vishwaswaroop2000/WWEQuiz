package com.example.wwequiz;

import java.io.Serializable;

public class User {
    private String name;
    private int level = 0;
    private int highScoreInEasyLevel = 0;
    private int highScoreInMediumLevel = 0;
    private int highScoreInHardLevel = 0;


    public User(String name){
        this.name = name;
    }

    public User(String name, int level, int highScoreInEasyLevel, int highScoreInMediumLevel, int highScoreInHardLevel) {
        this.name = name;
        this.level = level;
        this.highScoreInEasyLevel = highScoreInEasyLevel;
        this.highScoreInMediumLevel = highScoreInMediumLevel;
        this.highScoreInHardLevel = highScoreInHardLevel;
    }

    public String getName(){
        return name;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void setHighScoreInEasyLevel(int highScoreInEasyLevel){
        this.highScoreInEasyLevel = highScoreInEasyLevel;
    }

    public int getHighScoreInEasyLevel(){
        return highScoreInEasyLevel;
    }

    public void setHighScoreInMediumLevel(int highScoreInMediumLevel){
        this.highScoreInMediumLevel=highScoreInMediumLevel;
    }

    public int getHighScoreInMediumLevel(){
        return highScoreInMediumLevel;
    }

    public void setHighScoreInHardLevel(int highScoreInHardLevel){
        this.highScoreInHardLevel = highScoreInHardLevel;
    }

    public int getHighScoreInHardLevel(){
        return highScoreInHardLevel;
    }
}
