package com.oms.examinationsystem.pojo;

import android.accounts.AbstractAccountAuthenticator;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carson on 2015/7/10.
 */
public class Question implements Serializable {
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sterm=" + sterm +
                ", choices=" + choices +
                ", answers=" + answers +
                ", remark=" + remark +
                '}';
    }

    private int id;
    private String type;
    private ArrayList<String> sterm;
    private HashMap<String, Boolean> choices;
    private ArrayList<String> answers;
    private ArrayList<Image> image;
    private ArrayList<String> remark;


    public ArrayList<String> getRemark() {
        return remark;
    }

    public void setRemark(ArrayList<String> remark) {
        this.remark = remark;
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

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }


}
