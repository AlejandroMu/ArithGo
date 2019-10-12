package com.example.arithgo.model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDriver extends SQLiteOpenHelper {

    private static DBDriver instance;

    public static synchronized DBDriver getInstance(Context con){
        return instance=instance==null?new DBDriver(con):instance;
    }
    public static final String DB_NAME = "ArithGo";
    public static final int DB_VERSION = 2;

    //ESTUDENT TABLE
    public static final String ESTUDENT_TABLE="ESTUDENT";
    public static final String ESTUDENT_ID="ID";
    public static final String ESTUDENT_NAME="NAME";
    public static final String ESTUDENT_POINTS="POINTS";

    //ESTUD_PRODUCT TABLE
    public static final String ESTUD_PRODUCT_TABLE="ESTUD_PRODUCT";
    public static final String ESTUD_PRODUCT_ID="ID";
    public static final String ESTUD_ID="ESTUD_ID";
    public static final String PROD_ID="PRODUCT_ID";

    //PRODUCT TABLE
    public static final String PRODUCT_TABLE="PRODUCT_TABLE";
    public static final String PRODUCT_ID="ID";
    public static final String PRODUCT_NAME="NAME";
    public static final String PRODUCT_DESC="DESCRIPTION";
    public static final String PRODUCT_PRICE="PRICE";


    private DBDriver(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLES
        String createStudent="CREATE TABLE $TABLE ($ID TEXT PRIMARY KEY, $NAME TEXT, $POINTS INTEGER)";
        createStudent=createStudent.replace("$TABLE",ESTUDENT_TABLE)
                .replace("$ID",ESTUDENT_ID)
                .replace("$NAME",ESTUDENT_NAME)
                .replace("$POINTS",ESTUDENT_POINTS);

        String createPRODUCT="CREATE TABLE $TABLE ($ID TEXT PRIMARY KEY, $NAME TEXT, $DESC TEXT, $PRICE INTEGER)";
        createPRODUCT=createPRODUCT.replace("$TABLE",PRODUCT_TABLE)
                .replace("$ID",PRODUCT_ID)
                .replace("$NAME",PRODUCT_NAME)
                .replace("$DESC",PRODUCT_DESC)
                .replace("$PRICE",PRODUCT_PRICE);

        String createStudProd="CREATE TABLE $TABLE ($ID TEXT PRIMARY KEY, $STUDENT TEXT, $PROD TEXT, FOREIGN KEY ($STUDENT) REFERENCES $FES($FEID),FOREIGN KEY ($PROD) REFERENCES $FPROD($FPID))";
        createStudProd=createStudProd.replace("$TABLE",ESTUD_PRODUCT_TABLE)
                .replace("$ID",ESTUD_PRODUCT_ID)
                .replace("$STUDENT",ESTUD_ID)
                .replace("$PROD",PROD_ID)
                .replace("$FES",ESTUDENT_TABLE)
                .replace("$FEID",ESTUDENT_ID)
                .replace("$FPROD",PRODUCT_TABLE)
                .replace("$FPID",PRODUCT_ID);

        db.execSQL(createStudent);
        db.execSQL(createPRODUCT);
        db.execSQL(createStudProd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ESTUD_PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ESTUDENT_TABLE);
        onCreate(db);
    }
}