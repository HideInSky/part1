package com.example.part1;

import android.util.Log;

public class Note {
    private long ID;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    private String questionTitle;
    private String doneTimes;
    private String dateOfCreate;
    private String questionConclusion;
    private String img1Path;
    private String img2Path;


    Note(){    }



    Note(String questionTitle, String doneTimes, String dateOfCreate, String questionConclusion,
         String img1Path, String img2Path){
        this.questionTitle = questionTitle;
        this.doneTimes = doneTimes;
        this.dateOfCreate = dateOfCreate;
        this.questionConclusion = questionConclusion;
        this.img1Path = img1Path;
        this.img2Path = img2Path;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getDoneTimes() {
        return doneTimes;
    }

    public void setDoneTimes(String doneTimes) {
        this.doneTimes = doneTimes;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(String dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getQuestionConclusion() {
        return questionConclusion;
    }

    public void setQuestionConclusion(String questionConclusion) {
        this.questionConclusion = questionConclusion;
    }
    public String getImg1Path() {
        return img1Path;
    }

    public void setImg1Path(String img1Path) {
        this.img1Path = img1Path;
    }

    public String getImg2Path() {
        return img2Path;
    }

    public void setImg2Path(String img2Path) {
        this.img2Path = img2Path;
    }

    public void printNote(){
        String print = "ID " + ID + "\n"+
                "questionTitle " + questionTitle + "\n"+
                "doneTimes " + doneTimes + "\n"+
                "dateOfCreate " + dateOfCreate + "\n"+
                "questionConclusion " + questionConclusion + "\n"+
                "img1Path " + img1Path + "\n"+
                "img2Path " + img2Path + "\n";
        Log.d("printNote", print);


    }

}
