package com.example.healthcare;

import android.widget.TextView;

public class ChallengeData {
    private int id;
    private String image;
    private String title;
    private String recruit_deadline;
    private String challenge_period;
    private int member_id;

//    public ChallengeData(int id, String image, String title, String recruit_deadline, String challenge_period, int member_id) {
//        this.id = id;
//        this.image = image;
//        this.title = title;
//        this.recruit_deadline = recruit_deadline;
//        this.challenge_period = challenge_period;
//        this.member_id = member_id;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getRecruit_deadline(){
        return recruit_deadline;
    }

    public void setRecruit_deadline(String recruit_deadline){
        this.recruit_deadline = recruit_deadline;
    }

    public String getChallenge_period(){
        return challenge_period;
    }

    public void setChallenge_period(String challenge_period){
        this.challenge_period = challenge_period;
    }

}
