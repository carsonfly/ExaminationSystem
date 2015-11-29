package com.oms.examinationsystem.io;




import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.oms.examinationsystem.pojo.Question;
import com.oms.examinationsystem.pojo.QuestionData;
import com.oms.examinationsystem.pojo.QuestionLib;










/**
 * Created by carson on 2015/7/25.
 */
public class QuestionIO {




    public QuestionData readFile(InputStream inputStream){
        QuestionData data=null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
            data = ((QuestionData) objectInputStream.readObject());
            objectInputStream.close();
            inputStream.close();
        }catch (Exception e ){

        }
        return data;
    }
    public ArrayList<Question> readQuestionData(QuestionData data, int libCode)  {
        ArrayList<Question> questions=null;
            	for(QuestionLib lib:data.getQuestionLibs())
            	{
            		if (lib.getLibCode()==libCode)
                        questions=lib.getQuestions();
            	}
        return questions;
    }
}
