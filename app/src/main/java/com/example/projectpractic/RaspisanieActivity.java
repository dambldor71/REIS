package com.example.projectpractic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RaspisanieActivity extends AppCompatActivity {

    DBHelper dbHelperRasp;
    Button addRasp, checkRasp, backRasp;
    EditText sotrRasp, startRasp, endRasp;
    TextView weekday;
    DatePicker dataPicker;
    TextView dataRasp;
    Spinner dayRasp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspisanie_page);

        sotrRasp = (EditText) findViewById(R.id.raspName);
        startRasp = (EditText) findViewById(R.id.timePicker1);
        endRasp = (EditText) findViewById(R.id.timePicker2);

        dayRasp = (Spinner) findViewById(R.id.raspDay);

        addRasp = (Button) findViewById(R.id.raspAddBut);
        checkRasp = (Button) findViewById(R.id.raspCheckBut);
        backRasp = (Button) findViewById(R.id.raspBackBut);

        dataPicker = (DatePicker) findViewById(R.id.datePicker);

        weekday = (TextView) findViewById(R.id.weekday);

        dbHelperRasp = new DBHelper(this);
        SQLiteDatabase database = dbHelperRasp.getWritableDatabase();

        DateFormat format = new SimpleDateFormat("dd.MM");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] week_days;
        week_days = getResources().getStringArray(R.array.catNames);
        String[] data_days = new String[7];
        String[] data_days_fut = new String[7];


        for (int i = 0; i < 7; i++) {
            data_days[i] = format.format(calendar1.getTime());
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (int i = 0; i < 7; i++) {
            data_days_fut[i] = format.format(calendar1.getTime());
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d("datas", data_days[0] + " " + data_days[1] + " " + data_days[2] + " " + data_days[3] + " " +
                data_days[4] + " " + data_days[5] + " " + data_days[6]);
        Log.d("datas_fut", data_days_fut[0] + " " + data_days_fut[1] + " " + data_days_fut[2] + " " + data_days_fut[3] + " " +
                data_days_fut[4] + " " + data_days_fut[5] + " " + data_days_fut[6]);

        String[] dataArr = new String[1];
        dataPicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = "";
                String day = "";
                String month = "";
                if (monthOfYear + 1 < 10) {
                    month += "0" + (monthOfYear + 1);
                } else { month += (monthOfYear + 1);}

                if (dayOfMonth < 10) {
                    day += "0" + dayOfMonth;
                } else { day += dayOfMonth;}

                date += day + "." + month + "." + year;
                dataArr[0] = date;
                Log.d("datacheck","Дата: " + dataArr[0]);

                for (int i = 0; i < data_days.length; i += 1) {
                    Log.d("substr", date.substring(0, 5));
                    if (data_days[i].equals(date.substring(0, 5))) {
                        weekday.setText(week_days[i]);
                    } else if (data_days_fut[i].equals(date.substring(0, 5))) {
                        weekday.setText(week_days[i]);
                    }
                }
                // альтернативная запись
                // dateTextView.setText("Дата: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        });

        backRasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addRasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues raspCV = new ContentValues();

                String nameSotr = String.valueOf(sotrRasp.getText());
                String startTime = String.valueOf(startRasp.getText());
                String endTime = String.valueOf(endRasp.getText());
                String dayWork = String.valueOf(weekday.getText());
                String dataWork = dataArr[0];

                Log.d("check", nameSotr + " " + startTime + " " + endTime + " " + dayWork + " ");

                String[] column = new String[]{"_id"};
                String selection = "Name_day = ?";
                String[] selectionArgs = new String[]{dayWork};

                Integer day_id = 0;

                Cursor raspC1 = database.query(DBHelper.WEEK, column, selection, selectionArgs, null, null, null);
                if (raspC1.moveToFirst()) {
                    int idIndex = raspC1.getColumnIndex(DBHelper.ID_day);
                    do {
                        day_id = raspC1.getInt(idIndex);
                    } while (raspC1.moveToNext());
                }

                String[] column1 = new String[]{"Code_worker"};
                String selection1 = "Name = ?";
                String[] selectionArgs1 = new String[]{nameSotr};

                Integer code_id = 0;

                Cursor raspC2 = database.query(DBHelper.WORKERS, column1, selection1, selectionArgs1, null, null, null);
                if (raspC2.moveToFirst()) {
                    int CodeIndex = raspC2.getColumnIndex(DBHelper.ID_worker);

                    do {
                        code_id = raspC2.getInt(CodeIndex);
                    } while (raspC2.moveToNext());
                }
                if (code_id > 0 && day_id > 0 && startTime != "" && endTime != "") {
                    raspCV.put(DBHelper.CodeSotr_rasp, code_id);
                    raspCV.put(DBHelper.Day_rasp, day_id);
                    raspCV.put(DBHelper.Start_rasp, startTime);
                    raspCV.put(DBHelper.End_rasp, endTime);
                    raspCV.put(DBHelper.Data_rasp, dataWork);
                    database.insert(DBHelper.RASP, null, raspCV);
                } else {
                    Toast toast = Toast.makeText(RaspisanieActivity.this, "Поля заполнены неверно или неполностью", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        checkRasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor Crasp = database.query(DBHelper.RASP, null, null, null, null, null, null);
                if (Crasp.moveToFirst()) {
                    int sotrIndex = Crasp.getColumnIndex(DBHelper.CodeSotr_rasp);
                    int dayIndex = Crasp.getColumnIndex(DBHelper.Day_rasp);
                    int startIndex = Crasp.getColumnIndex(DBHelper.Start_rasp);
                    int endIndex = Crasp.getColumnIndex(DBHelper.End_rasp);
                    int dataIndex = Crasp.getColumnIndex(DBHelper.Data_rasp);

                    do {
                        Log.d("rasp", "Сотрудник: " + Crasp.getInt(sotrIndex) + " День недели: " + Crasp.getInt(dayIndex)
                                + " Дата: " + Crasp.getString(dataIndex) + " Время работы: " + Crasp.getString(startIndex) + " - " + Crasp.getString(endIndex));
                    } while (Crasp.moveToNext());
                }
            }
        });
    }
}
