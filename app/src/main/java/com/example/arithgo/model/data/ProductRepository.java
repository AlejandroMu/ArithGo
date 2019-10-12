package com.example.arithgo.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.arithgo.app.ArithApp;
import com.example.arithgo.model.driver.DBDriver;
import com.example.arithgo.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static void fillTable(){
        Product p=new Product("1","Lapicero Icesi","",20);
        Product p1=new Product("2","Cuaderno","",30);
        Product p2=new Product("3","Libreta Icesi","",40);
        Product p3=new Product("4","Camiseta Icesi","",80);
        Product p4=new Product("5","Saco Icesi","",100);

        insert(p);
        insert(p1);
        insert(p2);
        insert(p3);
        insert(p4);
    }

    public static void insert(Product pr){
        DBDriver driver=DBDriver.getInstance(ArithApp.getContex());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query="INSERT INTO $TABLE VALUES('$ID','$NAME','$DESC',$PRICE)";
        query=query.replace("$TABLE",DBDriver.PRODUCT_TABLE)
                .replace("$ID",pr.getId())
                .replace("$NAME",pr.getName())
                .replace("$DESC",pr.getDesc())
                .replace("$PRICE",pr.getPrice()+"");

        db.execSQL(query);
        db.close();

    }

    public static List<Product> getAllProducts(){
        DBDriver driver = DBDriver.getInstance(ArithApp.getContex());
        SQLiteDatabase db = driver.getReadableDatabase();
        String query="SELECT * FROM "+DBDriver.PRODUCT_TABLE;
        Cursor cursor=db.rawQuery(query,null);
        List<Product> ret=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                 String id=cursor.getString(cursor.getColumnIndex(DBDriver.PRODUCT_ID));
                 String name=cursor.getString(cursor.getColumnIndex(DBDriver.PRODUCT_NAME));
                 String desc=cursor.getString(cursor.getColumnIndex(DBDriver.PRODUCT_DESC));
                 long price=cursor.getLong(cursor.getColumnIndex(DBDriver.PRODUCT_PRICE));
                 Product pr=new Product(id,name,desc,price);
                 ret.add(pr);
            }while (cursor.moveToNext());
        }else{
            fillTable();
            return getAllProducts();
        }
        return ret;
    }
}
