package com.example.part1;

public class Note {
    private String questionTitle;
    private String doneTimes;
    private String dateOfCreate;
    private String questionConclusion;

    Note(){    }
    Note(String questionTitle, String doneTimes, String dateOfCreate, String questionConclusion){
        this.questionTitle = questionTitle;
        this.doneTimes = doneTimes;
        this.dateOfCreate = dateOfCreate;
        this.questionConclusion = questionConclusion;
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
}
