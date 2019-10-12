package com.example.arithgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.arithgo.app.ArithApp;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private RadioButton hard;
    private RadioButton medium;
    private RadioButton easy;

    private Button login;

    private boolean allow;

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hard=findViewById(R.id.hard_radio);
        medium=findViewById(R.id.medium_radio);
        easy=findViewById(R.id.easy_radio);

        login=findViewById(R.id.login);
        name=findViewById(R.id.name);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam=name.getText().toString();
                valid();
                if(!nam.isEmpty()&&allow){
                    ArithApp.setStudent(nam);
                    ArithApp.MODE=level;
                    startActivity(new Intent(LoginActivity.this,MapsActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Campos incompletos", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void valid(){
        if(hard.isChecked()){
            allow|=true;
            level=ArithApp.HARD;
        }else if(medium.isChecked()){
            level=ArithApp.MEDIUM;
            allow|=true;
        }else if(easy.isChecked()){
            level=ArithApp.EASY;
            allow|=true;
        }else{
            allow=false;
        }
    }
}
