package com.example.projectpractic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StavkaActivity extends AppCompatActivity {

    DBHelper dbHelperStavka;

    EditText stavkaName, stavkaSum;

    Button addStav, checkStav, backStav;

    DatePicker stavkaDate;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stavka_page);

        dbHelperStavka = new DBHelper(this);
        SQLiteDatabase databaseStav = dbHelperStavka.getWritableDatabase();

        stavkaName = (EditText) findViewById(R.id.stavkaName);
        stavkaSum = (EditText) findViewById(R.id.stavkaSum);

        addStav = (Button) findViewById(R.id.stavkaAddBut);
        checkStav = (Button) findViewById(R.id.stavkaCheckBut);
        backStav = (Button) findViewById(R.id.stavkaBackBut);

        stavkaDate = (DatePicker) findViewById(R.id.stavkaDate);

        backStav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addStav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues stc = new ContentValues();
                // ПРЕОБРАЗОВАНИЕ ДАТЫ
                String dayString = "";
                Integer day = stavkaDate.getDayOfMonth();
                if (day < 10) {
                    dayString = "0" + String.valueOf(day);
                } else {
                    dayString = String.valueOf(day);
                }

                String monthString = "";
                Integer month = stavkaDate.getMonth();
                if (month < 10) {
                    monthString = "0" + String.valueOf(month);
                } else {
                    monthString = String.valueOf(month);
                }

                // ПОЛУЧЕННЫЕ ДАННЫХ ИЗ ЭДИТТЕКСТ И ДАТАПИКЕР
                Integer AddSumStav = Integer.parseInt(String.valueOf(stavkaSum.getText()));
                String AddDolzhStav = String.valueOf(stavkaName.getText());
                String AddDateStav = dayString + "." + monthString + "." + stavkaDate.getYear();

                Log.d("check", String.valueOf(AddSumStav) + " " + AddDolzhStav + " " + AddDateStav);

                Integer AddingCode = 0;
                String[] columns = new String[] {"_id"};
                String selection = "Name_dolzh = ?";
                String[] selectionArgs = new String[] { AddDolzhStav };
                Cursor stcc = databaseStav.query(DBHelper.DOLZHNOSTI, columns, selection, selectionArgs, null, null, null);
                if (stcc.moveToFirst()) {
                    int idIndex = stcc.getColumnIndex(DBHelper.ID_dolzh);

                    do {
                        AddingCode = stcc.getInt(idIndex);
                    } while (stcc.moveToNext());
                }

                if (AddingCode > 0) {
                    Log.d("check", AddSumStav + ", " + AddingCode + " " + AddDolzhStav + ", " + AddDateStav);
                    stc.put(DBHelper.Sum_stavki, AddSumStav);
                    stc.put(DBHelper.Dolzh_stavki, AddingCode);
                    stc.put(DBHelper.Data_stavki, AddDateStav);
                    databaseStav.insert(DBHelper.STAVKA, null, stc);
                }
                else {
                    Toast toast = Toast.makeText(StavkaActivity.this, "Такой должности не существует", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        checkStav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor ccst = databaseStav.query(DBHelper.STAVKA, null, null, null, null, null, null);
                if (ccst.moveToFirst()) {
                    int idIndex = ccst.getColumnIndex(DBHelper.ID_stavki);
                    int sumIndex = ccst.getColumnIndex(DBHelper.Sum_stavki);
                    int dolzhIndex = ccst.getColumnIndex(DBHelper.Dolzh_stavki);
                    int dataIndex = ccst.getColumnIndex(DBHelper.Data_stavki);

                    do {
                        Log.d("stavki", ccst.getInt(idIndex) + ". " + ccst.getInt(sumIndex) + " " + ccst.getInt(dolzhIndex) + " " + ccst.getString(dataIndex));
                    } while (ccst.moveToNext());
                }
            }
        });

    }
}
