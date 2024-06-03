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

public class DolzhnostActivity extends AppCompatActivity {

    DBHelper dbHelperDolzh;

    EditText NameDolzh;
    Button AddDolzh;
    Button CheckDolzh;
    Button BackDolzh;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dolzhnost_page);

        dbHelperDolzh = new DBHelper(this);
        SQLiteDatabase databaseR = dbHelperDolzh.getWritableDatabase();

        NameDolzh = (EditText) findViewById(R.id.dolzhName);
        AddDolzh = (Button) findViewById(R.id.dolzhAddBut);
        CheckDolzh = (Button) findViewById(R.id.dolzhCheckBut);
        BackDolzh = (Button) findViewById(R.id.dolzhBackBut);

        BackDolzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AddDolzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cd = new ContentValues();
                String DolzhName = String.valueOf(NameDolzh.getText());
                cd.put(DBHelper.Name_dolzh, DolzhName);
                databaseR.insert(DBHelper.DOLZHNOSTI, null, cd);
            }
        });

        CheckDolzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor dc = databaseR.query(DBHelper.DOLZHNOSTI, null, null, null, null, null, null);
                if (dc.moveToFirst()) {
                    int IdIndex = dc.getColumnIndex(DBHelper.ID_dolzh);
                    int NameIndex = dc.getColumnIndex(DBHelper.Name_dolzh);

                    do {
                        Log.d("Должности", dc.getInt(IdIndex) + ". " + dc.getString(NameIndex));
                    } while (dc.moveToNext());
                }
            }
        });
    }
}
