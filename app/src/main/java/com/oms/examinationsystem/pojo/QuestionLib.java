package com.oms.examinationsystem.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by carson on 2015/7/10.
 */
public class QuestionLib implements Serializable {
    private ArrayList<Question> questions;
    private  String name;
    private int libCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

	public int getLibCode() {
		return libCode;
	}

	public void setLibCode(int libCode) {
		this.libCode = libCode;
	}

	@Override
	public String toString() {
		return "QuestionLib [ name=" + name
				+ ", libCode=" + libCode + "]";
	}
}
