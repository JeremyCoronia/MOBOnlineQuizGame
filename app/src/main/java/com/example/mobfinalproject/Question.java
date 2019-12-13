package com.example.mobfinalproject;

public class Question {
    private String Question, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer, CategoryID;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryID) {
        this.Question = question;
        this.AnswerA = answerA;
        this.AnswerB = answerB;
        this.AnswerC = answerC;
        this.AnswerD = answerD;
        this.CorrectAnswer = correctAnswer;
        this.CategoryID = categoryID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        this.AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        this.AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        this.AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        this.AnswerD = answerD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.CorrectAnswer = correctAnswer;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        this.CategoryID = categoryID;
    }
}
