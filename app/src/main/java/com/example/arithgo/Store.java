package com.example.arithgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arithgo.app.ArithApp;
import com.example.arithgo.model.data.ProductRepository;
import com.example.arithgo.model.entity.Product;

import java.util.List;

public class Store extends AppCompatActivity {

    private ListView list;
    private TextView points;
    private ArrayAdapter adapter;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        list=findViewById(R.id.list);
        points=findViewById(R.id.points);
        back=findViewById(R.id.back_button);

        String user= ArithApp.getStudent().toString();
        points.setText(user);
        List<Product> productList= ProductRepository.getAllProducts();

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,productList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Product p=(Product) adapter.getItem(pos);
                boolean ad=ArithApp.getStudent().addProduct(p);


                if(!ad){
                    Toast.makeText(Store.this,"Los puntos no son suficientes",Toast.LENGTH_LONG).show();
                }else{
                    String user= ArithApp.getStudent().toString();
                    points.setText(user);
                    Toast.makeText(Store.this,"El canje fue exitoso",Toast.LENGTH_SHORT).show();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Store.this,MapsActivity.class));
            }
        });


    }
}
