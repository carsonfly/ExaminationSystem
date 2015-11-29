package com.oms.examinationsystem.sort;

import android.util.Log;

import com.oms.examinationsystem.pojo.Question;

import java.util.Comparator;

/**
 * Created by carson on 2015/7/12.
 */
public class SortByRandom implements Comparator<Question> {
    @Override
    public int compare(Question lhs, Question rhs) {
        Integer r = ((int) (Math.random() * 3)) - 1;

        return r;
    }
}
