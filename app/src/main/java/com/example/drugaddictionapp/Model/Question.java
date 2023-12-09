package com.example.drugaddictionapp.Model;

public class Question {

    String ID, Question, Answer;

    public Question() {
    }

    public Question(String ID, String question, String answer) {
        this.ID = ID;
        Question = question;
        Answer = answer;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
