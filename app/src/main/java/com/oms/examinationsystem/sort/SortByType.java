package com.oms.examinationsystem.sort;

import com.oms.examinationsystem.pojo.Question;

import java.util.Comparator;

/**
 * Created by carson on 2015/7/12.
 */
public class SortByType implements Comparator<Question> {
    @Override
    public int compare(Question lhs, Question rhs) {
        return lhs.getType().compareTo(rhs.getType());
    }
}
