package com.example.postmortem;

public class User {

    private String username;
    private String password;

    private int score;
    private int hiscore;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.score = 0;
        this.hiscore = 0;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getHiscore(){
        return this.hiscore;
    }

    public void setHiscore(int hiscore){
        this.hiscore = hiscore;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }



}
