package com.oms.examinationsystem.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionData implements Serializable {
	private ArrayList<QuestionLib> questionLibs;

	public ArrayList<QuestionLib> getQuestionLibs() {
		return questionLibs;
	}

	public void setQuestionLibs(ArrayList<QuestionLib> questionLibs) {
		this.questionLibs = questionLibs;
	}

	@Override
	public String toString() {
		return "QuestionData [questionLibs=" + questionLibs + "]";
	}
	
}
