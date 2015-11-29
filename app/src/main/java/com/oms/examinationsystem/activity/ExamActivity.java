package com.oms.examinationsystem.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oms.examinationsystem.R;
import com.oms.examinationsystem.pojo.Answer;
import com.oms.examinationsystem.pojo.Question;
import com.oms.examinationsystem.pojo.QuestionLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Handler;

public class ExamActivity extends Activity {
    private TextView textView;
    private TextView textAnswer;
    private EditText editAnswer;
    private static ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private static ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private static ArrayList<Question> questions;
    private static HashMap<Integer, Answer> answers = new HashMap<>();
    private static int index = 0;
    private static boolean hasHandin = false;
    private static FrameLayout layout;
    private static Button next ;
    private static Button previous ;
    private static Button turnToPage;
    private static boolean isNightMode=false;
    public ExamActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        layout=(FrameLayout)findViewById(R.id.examLayout);
        Log.i("activity", "onCreate");
        radioButtons.clear();
        checkBoxes.clear();
        radioButtons.add((RadioButton) findViewById(R.id.radioButton1));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton2));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton3));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton4));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton5));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton6));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton7));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton8));

        checkBoxes.add((CheckBox) findViewById(R.id.checkBox1));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox2));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox3));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox4));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox5));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox6));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox7));
        checkBoxes.add((CheckBox) findViewById(R.id.checkBox8));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        QuestionLib lib = (QuestionLib) bundle.getSerializable("questionLib");
        textView = (TextView) findViewById(R.id.textView);
        textAnswer = (TextView) findViewById(R.id.textAnswer);
        editAnswer = (EditText) findViewById(R.id.editText);
        questions = lib.getQuestions();
        // Log.i("questions", String.valueOf(questions.size()));

        next = (Button) findViewById(R.id.nextQuestion);
       previous = (Button) findViewById(R.id.previousQuestion);
        turnToPage=(Button)findViewById(R.id.turnToPage);
        final Button handin = (Button) findViewById(R.id.handin);
        turnToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(ExamActivity.this);
                editText.setText("0");

                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("要跳转到多少题?(共"+questions.size()+"题)")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int page=0;
                                try {
                                 page = Integer.parseInt(editText.getText().toString())-1;

                                } catch (NumberFormatException e) {
                                    Log.i("Exception","NumberFormatException");
                                }
                                try {
                                    index=page;
                                    initQuestionView(page);

                                }catch (Exception e2){
                                    e2.printStackTrace();
                                    Log.i("Exception","QuestionInitException"+e2.toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < questions.size() - 1&&index>=0) {
//                    Log.i("answer",getAnswer(index).toString()+"__"+index);
                    answers.put(index, getAnswer(index));

                    index++;
                    initQuestionView(index);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    answers.put(index, getAnswer(index));
                    //  Log.i("answer",getAnswer(index).toString());
                    index--;
                    initQuestionView(index);
                }
            }
        });


        initQuestionView(index);
         }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            questions.clear();
            index=0;
            hasHandin=false;
            answers.clear();
           this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//     protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState!=null){
//            Log.i("onCreate",savedInstanceState.toString());
//            index=(int)savedInstanceState.get("index");
//            questions=(ArrayList<Question>)savedInstanceState.get("questions");
//            answers=(HashMap<Integer,Answer>)savedInstanceState.get("answers");
//            checkBoxes=(ArrayList<CheckBox>)savedInstanceState.get("checkBoxes");
//            radioButtons=(ArrayList<RadioButton>)savedInstanceState.get("radioButtons");
//            hasHandin=(Boolean)savedInstanceState.get("hasHandin");
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("onSaveInstanceState", outState.toString());
        super.onSaveInstanceState(outState);
        outState.putSerializable("questions", questions);
        outState.putSerializable("answers", answers);
        outState.putSerializable("index", index);

        //outState.putSerializable("hasHandin", hasHandin);
        Log.d("save", "from onsaveinstancestate");
        Log.i("onSaveInstanceState", outState.toString());
    }

    public Answer getAnswer(int index) {
        Answer answer = new Answer();
        answer.setType(questions.get(index).getType());
        answer.setId(questions.get(index).getId());
        answer.setSterm(questions.get(index).getSterm());
        HashMap<String, Boolean> choices = new HashMap<>();
        if (questions.get(index).getType().equals("单选题") || questions.get(index).getType().equals("判断题")) {
            for (int i = 0; i < questions.get(index).getChoices().size(); i++) {
                String choice = radioButtons.get(i).getText().toString();
                Boolean checked = radioButtons.get(i).isChecked();
                choices.put(choice, checked);
            }
        } else if (questions.get(index).getType().equals("多选题")) {
            for (int i = 0; i < questions.get(index).getChoices().size(); i++) {
                String choice = checkBoxes.get(i).getText().toString();

                Boolean checked = checkBoxes.get(i).isChecked();

                choices.put(choice, checked);
            }
        }else if (questions.get(index).getType().equals("填空题")){
            String text=editAnswer.getText().toString();
            Log.i("answerText",text);
            ArrayList<String> answerString=new ArrayList<>();
            answerString.add(text);
            answer.setAnswers(answerString);
        }
        answer.setChoices(choices);

        return answer;
    }

    protected void dialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        builder.setMessage(message);

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }


    public void initQuestionView(Integer id) {
        Question question=new Question();

        textView.setText("题库中没有该类型题目");
        textAnswer.setVisibility(View.GONE);
        editAnswer.setVisibility(View.GONE);
        for (int i = 0; i < 8; i++) {
            checkBoxes.get(i).setVisibility(View.GONE);
            radioButtons.get(i).setVisibility(View.GONE);
        }
        if (questions.size()>id)
        question = questions.get(id);


        Answer answer = null;
        if (answers.containsKey(id)) {
            answer = answers.get(id);
        }


        if (question != null&&question.getType()!=null&&(question.getType().equals("单选题") || question.getType().equals("判断题"))) {
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group);
            radioGroup.clearCheck();
            textView.setText(index+1+"."+question.getSterm().get(0));
            textAnswer.setVisibility(View.GONE);
            HashMap<String, Boolean> choices = question.getChoices();

            Iterator<String> iterator = choices.keySet().iterator();

            for (int i = 0; i < 8; i++) {

                checkBoxes.get(i).setVisibility(View.GONE);

                if (iterator.hasNext()) {
                    String choiceString = iterator.next();
                    radioButtons.get(i).setText(choiceString);
                    radioButtons.get(i).setTextColor(Color.BLACK);


                    if (answer != null
                            && answer.getChoices() != null
                            && answer.getChoices().get(choiceString) != null
                            && answer.getChoices().get(choiceString)) {
                        radioGroup.check(radioButtons.get(i).getId());
                    }

                    if (hasHandin) {
                        if (questions.get(id).getChoices().get(choiceString)) {
                            radioButtons.get(i).setTextColor(Color.RED);
                        }
                        if(question.getRemark()!=null&&question.getRemark().size()>0) {
                            textAnswer.setVisibility(View.VISIBLE);
                            textAnswer.setText(question.getRemark().get(0));
                            textAnswer.setTextColor(Color.RED);
                        }
                        if (answer != null
                                && answer.getChoices() != null
                                && answer.getChoices().get(choiceString) != null
                                && answer.getChoices().get(choiceString)) {
                            radioGroup.check(radioButtons.get(i).getId());
                        }
                    }

                    radioButtons.get(i).setVisibility(View.VISIBLE);

                } else {
                    radioButtons.get(i).setVisibility(View.GONE);
                }
            }

        } else if (question != null&&question.getType()!=null&&question.getType().equals("多选题")) {
            textView.setText(index+1+"."+question.getSterm().get(0));
            textAnswer.setVisibility(View.GONE);
            HashMap<String, Boolean> choices = question.getChoices();
            int size = choices.size();
            Iterator<String> iterator = choices.keySet().iterator();

            for (int i = 0; i < 8; i++) {
                radioButtons.get(i).setVisibility(View.GONE);

                if (i < size) {
                    String choiceString = iterator.next();
                    checkBoxes.get(i).setText(choiceString);
                    checkBoxes.get(i).setChecked(false);
                    checkBoxes.get(i).setTextColor(Color.BLACK);
                    if (answer != null && answer.getChoices() != null && answer.getChoices().get(choiceString) != null) {
                        checkBoxes.get(i).setChecked(answer.getChoices().get(choiceString));
                    }
                    if (hasHandin) {

                        if (question.getChoices().get(choiceString)) {
                            checkBoxes.get(i).setTextColor(Color.RED);
                        }

                    }
                    checkBoxes.get(i).setVisibility(View.VISIBLE);

                } else {
                    checkBoxes.get(i).setVisibility(View.GONE);
                }
            }

        } else if (question != null&&question.getType()!=null&&question.getType().equals("问答题")) {
            textView.setText(index+1+"."+question.getSterm().get(0));
            textAnswer.setVisibility(View.GONE);


            for (int i = 0; i < 8; i++) {
                radioButtons.get(i).setVisibility(View.GONE);
                checkBoxes.get(i).setVisibility(View.GONE);

            }
            if (hasHandin) {
                textAnswer.setVisibility(View.VISIBLE);
                textAnswer.setText(question.getAnswers().get(0));
                textAnswer.setTextColor(Color.RED);
            }

        }else if (question != null&&question.getType()!=null&&question.getType().equals("填空题")){
            editAnswer.setVisibility(View.VISIBLE);
            textView.setText(index+1+"."+question.getSterm().get(0));
            textAnswer.setVisibility(View.GONE);
            editAnswer.setText("");
            if (answer != null && answer.getChoices() != null && answer.getAnswers().get(0)!= null) {
                editAnswer.setText(answer.getAnswers().get(0));
            }

            for (int i = 0; i < 8; i++) {
                radioButtons.get(i).setVisibility(View.GONE);
                checkBoxes.get(i).setVisibility(View.GONE);

            }
            if (hasHandin) {
                textAnswer.setVisibility(View.VISIBLE);
                textAnswer.setText(question.getAnswers().get(0));
                textAnswer.setTextColor(Color.RED);
            }
        }
        setNightMode(isNightMode);




    }
    public void turnToPage(){
        int page=0;

    }
    public void handinPaper() {
        int total = 0;
        int right = 0;
        total = answers.size();
        int size = questions.size();
        hasHandin = true;

        for (int index : answers.keySet()) {
            if (choiceEquals(answers.get(index).getChoices(), questions.get(index).getChoices())) {
                right++;
                answers.get(index).setRight(true);
            } else if (questions.get(index).getType().equals("填空题")) {
                if (questions.get(index).getAnswers().get(0).matches(".*"+answers.get(index).getAnswers().get(0)+".*")){
                    right++;
                    answers.get(index).setRight(true);
                }
            } else {
                answers.get(index).setRight(false);
            }
        }
        initQuestionView(index);
        dialog("你一共做了" + total + "道题,作对了" + right + "道题.题库共有" + size + "道题");

    }

    public boolean choiceEquals(HashMap<String, Boolean> a, HashMap<String, Boolean> b) {
        boolean r = true;
        for (String key : a.keySet()) {
            if (!a.get(key).equals(b.get(key)))
                r = false;
        }
        return r;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.handin) {
            handinPaper();
            return true;
        }else if (id==R.id.refresh){
            hasHandin=false;
            answers.clear();

        }else if (id== R.id.model&&item.getTitle().equals("夜间模式")){
            isNightMode=true;
            setNightMode(isNightMode);
            item.setTitle("日间模式");
        }else if (id== R.id.model&&item.getTitle().equals("日间模式")){

            isNightMode=false;
            setNightMode(isNightMode);
            item.setTitle("夜间模式");
        }

        return super.onOptionsItemSelected(item);
    }
    private void setNightMode(boolean mode){
        if (mode){
            layout.setBackgroundColor(Color.BLACK);
            textView.setTextColor(Color.WHITE);
            next.setTextColor(Color.WHITE);
            turnToPage.setTextColor(Color.WHITE);
            previous.setTextColor(Color.WHITE);
            for(CheckBox checkBox:checkBoxes)
            {
                if (checkBox.getCurrentTextColor()!=Color.RED)
                checkBox.setTextColor(Color.WHITE);
            }

            for(RadioButton radioButton:radioButtons){
                if (radioButton.getCurrentTextColor()!=Color.RED)
                radioButton.setTextColor(Color.WHITE);
            }

        }else {
            layout.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
            next.setTextColor(Color.BLACK);
            turnToPage.setTextColor(Color.BLACK);
            previous.setTextColor(Color.BLACK);
            for(CheckBox checkBox:checkBoxes){
                if (checkBox.getCurrentTextColor()!=Color.RED)
                    checkBox.setTextColor(Color.BLACK);
            }

            for(RadioButton radioButton:radioButtons){
                if (radioButton.getCurrentTextColor()!=Color.RED)
                radioButton.setTextColor(Color.BLACK);
            }

        }
    }
}
