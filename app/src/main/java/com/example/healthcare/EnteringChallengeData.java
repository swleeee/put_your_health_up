package com.example.healthcare;

public class EnteringChallengeData {
    private int id;
    private String image;
    private String title;
    private int is_success;
    private String kinds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setIs_success(int is_success) {
        this.is_success = is_success;
    }

    public int getIs_success() {
        return is_success;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }
}