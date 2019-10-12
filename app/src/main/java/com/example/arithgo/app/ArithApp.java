package com.example.arithgo.app;

import android.app.Application;
import android.content.Context;

import com.example.arithgo.model.data.EstudentRepository;
import com.example.arithgo.model.entity.Student;

public class ArithApp extends Application {

    public static final int HARD=3;
    public static final int MEDIUM=2;
    public static final int EASY=1;


    private static Context contex;

    private static Student student;

    public static int MODE=1;

    @Override
    public void onCreate() {
        super.onCreate();

        ArithApp.contex=getApplicationContext();
    }

    public static Context getContex(){
        return contex;
    }

    public static Student getStudent() {
        return student;
    }

    public static void setStudent(String studen) {
        if(studen!=null){
            student= EstudentRepository.getStudentCreate(studen);
        }else{
            student=null;
        }

    }
}
