package com.example.healthcare;

public class DayData{
    private String title;
    private int image;

//    public DayData(String title, int image){
//        this.title = title;
//        this.image = image;
//    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
