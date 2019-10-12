package com.example.arithgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arithgo.app.ArithApp;
import com.example.arithgo.model.data.EstudentRepository;
import com.example.arithgo.model.entity.Student;

public class Challenge extends AppCompatActivity {

    private Button send;
    private TextView view;
    private EditText answer;


    private int o1;
    private int o2;
    private int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        send=findViewById(R.id.button);
        view=findViewById(R.id.view);
        answer=findViewById(R.id.text);

        o1=(int)(Math.random()*20*ArithApp.MODE)+2;
        o2=(int)(Math.random()*10*ArithApp.MODE)+1;
        op=(int)(Math.random()*2)+2*(ArithApp.MODE-1);


        view.setText("Resuelva \n "+operacion(o1,o2,op));

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String an=answer.getText().toString();
                double r=Double.parseDouble(an);
                Student es=ArithApp.getStudent();
                Toast t;
                if(r==getResponse(o1,o2,op)){
                    es.setPoints(es.getPoints()+ArithApp.MODE);
                    t=Toast.makeText(Challenge.this,"Respuesta Correcta",Toast.LENGTH_SHORT);
                }else{
                    es.setPoints(es.getPoints()-ArithApp.MODE);
                    t=Toast.makeText(Challenge.this,"Respuesta Incorrecta",Toast.LENGTH_SHORT);
                }
                t.show();

                EstudentRepository.updatePoint(es);
                Intent i=new Intent(Challenge.this,MapsActivity.class);
                startActivity(i);
            }
        });
    }
    private String operacion(int o1,int o2, int ope){
        switch (ope){
            case 0:{
                return o1+"+"+o2;
            }
            case 1:{
                return o1+"-"+o2;
            }
            case 2:{
                return o1+"*"+o2;
            }
            case 3:{
                return o1+"/"+o2;
            }
            case 4:{
                return "("+o1+"+"+o2+")*("+o1+"-"+o2+")";
            }
            case 5:{
                return "("+o1+"+"+o2+")/("+o1+"-"+o2+")";
            }

        }
        return "";
    }

    private double getResponse(int o1,int o2, int ope){
        switch (ope){
            case 0:{
                return o1+o2;
            }
            case 1:{
                return o1-o2;
            }
            case 2:{
                return o1*o2;
            }
            case 3:{
                return (o1*1.0)/o2;
            }
            case 4:{
                return (o1+o2)*(o1-o2);
            }
            case 5:{
                return ((o1+o2)*1.0)/(o1-o2);
            }
        }
        return 0;
    }


}
