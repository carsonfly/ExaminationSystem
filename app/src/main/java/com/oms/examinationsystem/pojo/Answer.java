package com.oms.examinationsystem.pojo;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carson on 2015/7/10.
 */
public class Answer implements Serializable{
    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", choices=" + choices.toString() +
                '}';
    }

    private int id;
    private String type;
    private ArrayList<String> sterm;
    private HashMap<String, Boolean> choices;
    private ArrayList<String> answers;
    private Boolean right = false;

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSterm() {
        return sterm;
    }

    public void setSterm(ArrayList<String> sterm) {
        this.sterm = sterm;
    }

    public HashMap<String, Boolean> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<String, Boolean> choices) {
        this.choices = choices;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }


}
