package com.example.mobfinalproject;

public class Score {
    private String userScore;
    private String user;
    private String score;
    private String CategoryID;
    private String CategoryName;

    public Score() {
    }

    public Score(String userScore, String user, String score, String categoryID, String categoryName) {
        this.userScore = userScore;
        this.user = user;
        this.score = score;
        CategoryID = categoryID;
        CategoryName = categoryName;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
