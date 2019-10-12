package com.example.arithgo.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.arithgo.app.ArithApp;
import com.example.arithgo.model.driver.DBDriver;
import com.example.arithgo.model.entity.Product;
import com.example.arithgo.model.entity.Student;

import java.util.List;
import java.util.UUID;

public class EstudentRepository {

    public static Student getStudentCreate(String name){

        Student es=getStudent(name);

        if(es==null){
            Student estud=new Student(UUID.randomUUID().toString(),name,0);
            insert(estud);
            return estud;
        }
        return es;
    }

    public static void insert(Student student){
        DBDriver driver=DBDriver.getInstance(ArithApp.getContex());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query="INSERT INTO $TABLE VALUES('$ID','$NAME',$POINTS)";
        query=query.replace("$TABLE",DBDriver.ESTUDENT_TABLE)
                .replace("$ID",student.getId())
                .replace("$NAME",student.getName())
                .replace("$POINTS",student.getPoints()+"");

        db.execSQL(query);
        List<Product> products=student.getProducts();
        if(products!=null){
            for (Product pro:products) {
               String q= insertR(pro,student);
               db.execSQL(q);
            }
        }

        db.close();
    }

    public static String insertR(Product pro,Student est){
        String query="INSERT INTO $TABLE VALUES($ID,'$ST','$PROD')";
        query=query.replace("$TABLE",DBDriver.ESTUD_PRODUCT_TABLE)
                .replace("$ST",est.getId())
                .replace("$PROD",pro.getId())
                .replace("$ID", UUID.randomUUID().toString());
        return  query;
    }

    public static  Student getStudent(String nameS){
        DBDriver driver = DBDriver.getInstance(ArithApp.getContex());
        SQLiteDatabase db = driver.getReadableDatabase();
        String query="SELECT * FROM "+DBDriver.ESTUDENT_TABLE+ " e WHERE e.name='"+nameS+"'";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            String id=cursor.getString(cursor.getColumnIndex(DBDriver.ESTUDENT_ID));
            long points=cursor.getLong(cursor.getColumnIndex(DBDriver.ESTUDENT_POINTS));
            Student student=new Student(id,nameS,points);
            return student;
        }
        db.close();
        return null;
    }

    public static void updatePoint(Student s){
        String query="UPDATE $TABLE \n" +
                "    SET $FIELD = $VALUE \n" +
                "    WHERE [$IDF] = '$ID'";
        query=query.replace("$TABLE",DBDriver.ESTUDENT_TABLE)
                .replace("$FIELD",DBDriver.ESTUDENT_POINTS)
                .replace("$VALUE",s.getPoints()+"")
                .replace("$IDF",DBDriver.ESTUDENT_ID)
                .replace("$ID",s.getId());
        DBDriver driver = DBDriver.getInstance(ArithApp.getContex());
        SQLiteDatabase db = driver.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
