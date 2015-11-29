package com.oms.examinationsystem.io;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by carson on 2015/11/19.
 */
public class FileIO {
    private static FileIO fileIO;
    private FileIO(){}
    public static FileIO getInstance(){
        if (fileIO !=null){
            return fileIO;
        }else {
            fileIO =new FileIO();
            return fileIO;
        }
    }
    public void Save(String fileName,Object object,Context context){
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        String sdCardRoot=Environment.getExternalStorageDirectory().getAbsolutePath();
        String path=sdCardRoot+ File.separator+"com.oms.examination"+ File.separator+fileName;
        File file=new File(path);
        file.getParentFile().mkdirs();

        try{
            file.createNewFile();
            Log.i("SdCardIO",file.toString()+"\n");
            fileOutputStream=new FileOutputStream(path);
            bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            objectOutputStream=new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(object);

        }catch (IOException e){

            Log.e("IOException",path+e.toString()+"\n");
        }finally {
            try {
                objectOutputStream.close();
                bufferedOutputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {

            }
        }
    }

    public Object read(String fileName,Context context){
        Object o=null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        ObjectInputStream objectInputStream = null;
        String sdCardRoot=Environment.getExternalStorageDirectory().getAbsolutePath();
        String path=sdCardRoot+ File.separator+"com.oms.examination"+ File.separator+fileName;
        try{

            fileInputStream=new FileInputStream(path);
            bufferedInputStream=new BufferedInputStream(fileInputStream);
            objectInputStream=new ObjectInputStream(bufferedInputStream);
            o=objectInputStream.readObject();

        }catch (IOException e){
            Toast.makeText(context,"无法读取"+fileName,Toast.LENGTH_SHORT);
        } catch (ClassNotFoundException e) {
            Toast.makeText(context,"文件错误:"+fileName,Toast.LENGTH_SHORT);

        } finally {
            try {
                objectInputStream.close();
                bufferedInputStream.close();
                fileInputStream.close();
            }catch (Exception e){
                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG);
            }
        }

        return  o;
    }
}
