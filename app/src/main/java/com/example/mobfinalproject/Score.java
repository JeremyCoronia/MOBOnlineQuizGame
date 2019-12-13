package com.example.mobfinalproject;

public class Score {
    private String userScore;
    private String user;
    private String score;

    public Score() {
    }

    public Score(String userScore, String user, String score) {
        this.userScore = userScore;
        this.user = user;
        this.score = score;
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
}
