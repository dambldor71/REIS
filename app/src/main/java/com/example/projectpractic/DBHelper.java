package com.example.projectpractic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    // БАЗА ДАННЫХ
    public static final String DATABASE_NAME = "TestDrive18";

    // ТАБЛИЦЫ
    public static final String WORKERS = "Workerable";
    public static final String WISHES = "Wishes_table";
    public static final String DOLZHNOSTI = "Dolzhosti";
    public static final String STAVKA = "Stavki";
    public static final String WEEK = "Week";
    public static final String RASP = "Redact_raspisanie";


    public static final String ID_worker = "Code_worker";
    public static final String Name_worker = "Name";
    public static final String Code_dolzh = "Code_dolzh";


    public static final String ID_pozhel = "_id";
    public static final String Code_sotr = "Code_sotr";
    public static final String Wanted_days = "Wanted_days";
    public static final String Comments = "Comments";


    public static final String ID_dolzh = "_id";
    public static final String Name_dolzh = "Name_dolzh";


    public static final String ID_stavki = "_id";
    public static final String Sum_stavki = "Sum_stavki";
    public static final String Dolzh_stavki = "Dolzh_stavki";
    public static final String Data_stavki = "Data_stavki";


    public static final String ID_day = "_id";
    public static final String Name_day = "Name_day";

    public static final String ID_rasp = "_id";
    public static final String CodeSotr_rasp = "Code_sotr_rasp";
    public static final String Day_rasp = "Day_of_rasp";
    public static final String Start_rasp = "Time_start_rasp";
    public static final String End_rasp = "Time_end_rasp";
    public static final String Data_rasp = "Data_rasp";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WORKERS + " (" + ID_worker + " integer primary key," +
                Name_worker + " text," + Code_dolzh + " integer," +
                " foreign key (" + Code_dolzh + ") references " + DOLZHNOSTI + " (" +
                ID_dolzh + ")" + ")");

        db.execSQL("create table " + WISHES + " (" + ID_pozhel + " integer primary key," +
                Code_sotr + " integer," + Wanted_days + " text," + Comments + " text," +
                " foreign key (" + Code_sotr + ") references " + WORKERS + " (" + ID_worker + ")" + ")");

        db.execSQL("create table " + DOLZHNOSTI + " (" + ID_dolzh + " integer primary key," +
                Name_dolzh + " text unique" + ")");

        db.execSQL("create table " + STAVKA + " (" + ID_stavki + " integer primary key," +
                Sum_stavki + " integer," + Dolzh_stavki + " integer," + Data_stavki + " text," +
                " foreign key (" + Dolzh_stavki + ") references " + DOLZHNOSTI + " (" + ID_dolzh + ")" + ")");

        db.execSQL("create table " + WEEK + " (" + ID_day + " integer primary key," +
                Name_day + " text unique" + ")");

        db.execSQL("create table " + RASP + " (" + ID_rasp + " integer primary key," +
                CodeSotr_rasp + " integer," + Day_rasp + " integer," + Data_rasp + " text,"
                + Start_rasp + " text," + End_rasp + " text," +
                " foreign key (" + CodeSotr_rasp + ") references " + WORKERS + " (" + ID_worker + ")," +
                " foreign key (" + Day_rasp + ") references " + WEEK + " (" + ID_day + ")" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists " + WORKERS);
        //onCreate(db);
    }
}
