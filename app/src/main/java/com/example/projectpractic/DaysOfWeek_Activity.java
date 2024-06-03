package com.example.projectpractic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DaysOfWeek_Activity extends AppCompatActivity {

    DBHelper dbHelperweek;
    EditText weekName;

    Button weekBack, weekAdd, weekCheck;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.daysofweek_but);

        dbHelperweek = new DBHelper(this);
        SQLiteDatabase database = dbHelperweek.getWritableDatabase();

        weekName = (EditText) findViewById(R.id.weekName);
        weekAdd = (Button) findViewById(R.id.weekAddBut);
        weekCheck = (Button) findViewById(R.id.weekCheckBut);
        weekBack = (Button) findViewById(R.id.weekBackBut);

        weekBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        weekAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cD = new ContentValues();
                String addDay = String.valueOf(weekName.getText());
                cD.put(DBHelper.Name_day, addDay);
                database.insert(DBHelper.WEEK, null, cD);
            }
        });

        weekCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor Cd = database.query(DBHelper.WEEK, null, null, null, null, null, null);
                if (Cd.moveToFirst()) {
                    int dayIndex = Cd.getColumnIndex(DBHelper.Name_day);

                    do {
                        Log.d("days", Cd.getString(dayIndex));
                    } while (Cd.moveToNext());
                }
            }
        });
    }
}
