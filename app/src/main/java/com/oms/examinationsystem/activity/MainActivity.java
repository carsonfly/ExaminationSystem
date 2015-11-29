package com.oms.examinationsystem.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.oms.examinationsystem.R;
import com.oms.examinationsystem.io.FileIO;
import com.oms.examinationsystem.io.QuestionIO;
import com.oms.examinationsystem.pojo.Question;
import com.oms.examinationsystem.pojo.QuestionData;
import com.oms.examinationsystem.pojo.QuestionLib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends Activity {

    private ArrayList<Integer> questionNumber = new ArrayList<>();
    private TextView quesionLibView;
    private int libCode = 0;
    private static  int questionLibType=0;
    private QuestionIO questionIO=new QuestionIO();
    private FileIO fileIO=FileIO.getInstance();
    private ArrayList<Question> questions=null;
    private static QuestionData data=null;
    private static QuestionData mydata=null;
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("state", "onRestoreInstanceState");

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("state", "onSaveInstanceState");
        outState.putSerializable("libCode", libCode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data=(QuestionData)fileIO.read("QuestionLibData",getApplicationContext());
        mydata=(QuestionData)fileIO.read("MyQuestionLibData",getApplicationContext());
       if(data==null){
            data=questionIO.readFile(getResources().openRawResource(R.raw.lib));
           fileIO.Save("QuestionLibData",data,getApplicationContext());
       }
        questions=getQuestions();
        setContentView(R.layout.activity_main);

        Log.i("state", "onCreate");
        quesionLibView = (TextView) findViewById(R.id.quesionLib);
        quesionLibView.setText("题库:线路安规");
        final Button buttonShowAllQuestions = (Button) findViewById(R.id.showAllQuestions);
        final Button panduanti = (Button) findViewById(R.id.panduanti);
        final Button danxuanti = (Button) findViewById(R.id.danxuanti);
        final Button duoxuanti = (Button) findViewById(R.id.duoxuanti);
        final Button wendati = (Button) findViewById(R.id.wendati);
        final Button sortByRandomButton = (Button) findViewById(R.id.sortByRandom);
        final Button sortBySterm = (Button) findViewById(R.id.sorBySterm);
        final Button tiankongti = (Button) findViewById(R.id.tiankongti);

        this.getOverflowMenu();
        questionNumber=new ArrayList<Integer>();
        for(int i=0;i<5;i++)
            questionNumber.add(getQuestions().size());
        buttonShowAllQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collections.sort(questions,new SortByType());
                startExamActivity(questions, null, "all");

            }
        });
        panduanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExamActivity(questions, null, "判断题");
            }
        });

        tiankongti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExamActivity(questions, null, "填空题");

            }
        });
        danxuanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExamActivity(questions, null, "单选题");

            }
        });
        duoxuanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startExamActivity(questions, null, "多选题");

            }
        });
        wendati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExamActivity(questions, null, "问答题");

            }
        });

        sortByRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<Question> list = Arrays.asList((Question[]) questions.toArray(new Question[questions.size()]));
                Collections.shuffle(list);
                questions.clear();
                for (Question q : list) {
                    questions.add(q);
                }

                startExamActivity(questions, questionNumber, "random");

            }
        });
        sortBySterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final  EditText editText=new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("查找")
                        .setMessage("请输入部分题干")
                        .setView(editText)
                        .setPositiveButton("查找",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {

                                ArrayList<Question> searchQuestions=new ArrayList<Question>();
                                String s=editText.getText().toString();
                                String searchs[];
                                if (s.contains(" ")){
                                     searchs= s.split(" ");
                                }else {
                                    searchs=new String[1];
                                    searchs[0]=s;
                                }

                                for (Question question:questions){
                                    boolean match=false;
                                    for(int i=0;i<searchs.length;i++){
                                        Log.i("search",searchs[i]+"-");
                                        if (question.getSterm().get(0).matches(".*"+searchs[i].trim()+".*")){
                                            match=true;
                                            Log.i("search","get"+question.toString());
                                        }else {
                                            match=false;

                                            break;
                                        }
                                    }

                                    if(match)
                                    searchQuestions.add(question);
                                    Log.i("search","SearchQuestionsSize:"+searchQuestions.size());
                                }


                                startExamActivity(searchQuestions,questionNumber,"all");

                            }
                        })
                        .show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeQuestionLib) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请选择")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setSingleChoiceItems(new String[]{"2015-06安规考题","调度自动化技师考题","双千人才题库","2015-08安规考题"}, 0,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setTitle("请选择")
                                                    .setIcon(android.R.drawable.ic_dialog_info)
                                                    .setSingleChoiceItems(new String[]{"线路安规", "变电安规", "配电安规"}, 0,new DialogInterface.OnClickListener(){
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case 0:
                                                                    quesionLibView.setText("题库:线路安规");
                                                                    libCode = 0;
                                                                    questions=getQuestions();
                                                                    break;
                                                                case 1:
                                                                    quesionLibView.setText("题库:变电安规");
                                                                    libCode = 1;
                                                                    questions=getQuestions();
                                                                    break;
                                                                case 2:
                                                                    quesionLibView.setText("题库:配电安规");
                                                                    libCode = 2;
                                                                    questions=getQuestions();
                                                                    break;
                                                            }
                                                            dialog.dismiss();
                                                        }
                                                    })
                                                    .show();

                                        case 1:
                                            quesionLibView.setText("题库:调度自动化技师考题");
                                            libCode = 3;
                                            questions=getQuestions();
                                            break;
                                        case 2:
                                            quesionLibView.setText("题库:双千人才题库");
                                            libCode = 4;
                                            questions=getQuestions();
                                            break;
                                        case 3:
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setTitle("请选择")
                                                    .setIcon(android.R.drawable.ic_dialog_info)
                                                    .setSingleChoiceItems(new String[]{"线路安规", "变电安规", "配电安规"}, 0,new DialogInterface.OnClickListener(){
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    switch (which) {
                                                                        case 0:
                                                                            quesionLibView.setText("题库:线路安规");
                                                                            libCode = 5;
                                                                            questions=getQuestions();
                                                                            break;
                                                                        case 1:
                                                                            quesionLibView.setText("题库:变电安规");
                                                                            libCode = 6;
                                                                            questions=getQuestions();
                                                                            break;
                                                                        case 2:
                                                                            quesionLibView.setText("题库:配电安规");
                                                                            libCode = 7;
                                                                            questions=getQuestions();
                                                                            break;
                                                                    }
                                                                    dialog.dismiss();
                                                                }
                                                    })
                                                    .show();

                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        } else if (id == R.id.changeQuestionNumber) {
            final EditText editText = new EditText(this);
            editText.setText("20-20-20-20-20");
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请输入考试题量设置:单选题-多选题-判断题-问答题-填空题数量(例如25-25-30-20-0)")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                String[] numbers=editText.getText().toString().split("-");
                                questionNumber.clear();
                                    for(String number:numbers){

                                        questionNumber.add(Integer.parseInt(number));
                                        Log.i("exception",questionNumber.toString());
                                    }


                            } catch (NumberFormatException e) {

                                questionNumber.clear();
                                for(int i=0;i<5;i++)
                                    questionNumber.add(getQuestions().size());
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
            Log.i("exception", questionNumber.toString());
        } else if (id == R.id.about) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("关于").setMessage(R.string.about)
                    .setPositiveButton("确定", null).show();
        }else if (id == R.id.update) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("版本信息")
                    .setMessage(R.string.VerbInformation)
                    .setPositiveButton("确定", null).show();
        }else if (id==R.id.myQuestionLib){
            if(mydata!=null){
                ArrayList<String> titles=new ArrayList<>();
                final ArrayList<Integer> code=new ArrayList<>();
                for(QuestionLib lib:mydata.getQuestionLibs()){
                    Log.i("QuestionLib",mydata.getQuestionLibs().toString());
                    titles.add(lib.getName());
                    code.add(lib.getLibCode());
                }
                String[] titleStrings=new String[titles.size()];
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(titles.toArray(titleStrings), 0, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                libCode = code.get(which);
                                questions = getQuestions();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("错误")
                        .setMessage("我的题库文件不存在")
                        .setPositiveButton("确定", null).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Question> getQuestions() {



        ArrayList<Question> questions=questionIO.readQuestionData(data, libCode);

        Log.i("QuestionLibs",data.getQuestionLibs().toString());
        return questions;
    }

    public void startExamActivity(ArrayList<Question> questions, ArrayList<Integer> numbers, String type) {
        QuestionLib lib = new QuestionLib();
        ArrayList<Question> examQuestions = new ArrayList<>();
        examQuestions.clear();
        //if numbers equals null ,set every number max;
        if (numbers==null){
            numbers=new ArrayList<Integer>();
            for(int i=0;i<5;i++)
            numbers.add(questions.size());
        }

        if (type !=null&&type.equals("all")&&questions.size()>0) {
            for (int i = 0; i < questions.size(); i++)
                examQuestions.add(questions.get(i));
        } else if (type != null && type.equals("单选题")) {
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getType().equals("单选题")) {
                    examQuestions.add(questions.get(i));
                }
            }
        } else if (type != null && type.equals("多选题")) {
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getType().equals("多选题")) {
                    examQuestions.add(questions.get(i));
                }
            }
        } else if (type != null && type.equals("判断题")) {
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getType().equals("判断题")) {
                    examQuestions.add(questions.get(i));
                }
            }
        } else if (type != null && type.equals("问答题")) {
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getType().equals("问答题")) {
                    examQuestions.add(questions.get(i));
                }
            }
        }else if (type != null && type.equals("填空题")) {
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getType().equals("填空题")) {
                    examQuestions.add(questions.get(i));
                }
            }
        }else if (type != null && type.equals("random")) {
            int number=0;
            boolean stop=false;
            Log.i("exception",questionNumber.toString());
            for (int i = 0; i < questions.size() &&!stop; i++) {
                if (questions.get(i).getType().equals("单选题")) {

                    number++;
                    Log.i("examQuestions.size",String.valueOf(number)+" "+String.valueOf(questionNumber.get(0)));
                    if( number >questionNumber.get(0)){
                        stop=true;
                        break;
                    }
                    examQuestions.add(questions.get(i));
                }
            }

            number=0;
            stop=false;
            for (int i = 0; i < questions.size()&&!stop ; i++) {
                if (questions.get(i).getType().equals("多选题")) {

                    number++;
                    if( number >questionNumber.get(1)){
                        stop=true;
                        break;
                    }
                    examQuestions.add(questions.get(i));
                }
            }
            number=0;
            stop=false;
            for (int i = 0; i < questions.size()&&!stop ; i++) {
                if (questions.get(i).getType().equals("判断题")) {
                    number++;
                    if( number >questionNumber.get(2)){
                        stop=true;
                        break;
                    }
                    examQuestions.add(questions.get(i));
                }
            }
            number=0;
            stop=false;

            for (int i = 0; i < questions.size() &&!stop; i++) {
                if (questions.get(i).getType().equals("问答题")) {
                    number++;
                    if( number >questionNumber.get(3)){
                        stop=true;
                        break;
                    }
                    examQuestions.add(questions.get(i));
                }
            }
            number=0;
            stop=false;
            for (int i = 0; i < questions.size()&&!stop; i++) {
                if (questions.get(i).getType().equals("填空题")) {
                    number++;
                    if( number >questionNumber.get(4)){
                        stop=true;
                        break;
                    }
                    examQuestions.add(questions.get(i));
                }
            }
        }
        Log.i("examQuestions.size",String.valueOf(examQuestions.size()));

        lib.setQuestions(examQuestions);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("questionLib", lib);
        intent.putExtra("bundle", bundle);
        intent.setClass(MainActivity.this, ExamActivity.class);
        startActivity(intent);

    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
                Log.i("state", "enable overflowMenu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
