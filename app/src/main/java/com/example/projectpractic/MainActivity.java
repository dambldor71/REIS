package com.example.projectpractic;


import static java.lang.System.in;
import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //СОЗДАНИЕ ПЕРЕМЕННЫХ
    ImageButton CalendarBut;
    ImageButton OtchetBut;
    ImageButton ShablonBut;
    ImageButton DopolnBut;
    Button GenerateBut;

    Button AddWorker;
    TextView NameShow1, NameShow2, NameShow3;
    Button CheckWorkDaysBut;
    ConstraintLayout cal, otch, sha, dop;
    DBHelper dbHelperMain;

    SwitchCompat sw1, sw2, sw3, sw4, sw5, sw6, sw7;
    Button SendPol;
    EditText CommentPozhel;
    Button checkPozhel;
    Button addDolzhnost;
    Button addStavka;
    Button DaysOfWeek;

    Spinner showRaspsisanie;
    Button RaspisanieBut;
    Button pastWeek, nextWeek;

    TextView textdlya, textdlya2, textdlya3, textdlya4, textdlya5, textdlya6;
    TextView textdlya7, textdlya8, textdlya9, textdlya10, textdlya11, textdlya12;
    TextView dataShow;

    Spinner OtchetMonthSpinner;
    EditText OtchetYear;
    TextView KolSmens, KolHours1, KolHours2, KolHoursChill, KolStavka, KolMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        //ИНИЦИАЛИЗАЦИЯ ОБЪЕКТОВ ФАЙЛОВ XML
        Button BackRegistr = (Button) findViewById(R.id.Inviz);
        CalendarBut = (ImageButton) findViewById(R.id.CalButton);
        OtchetBut = (ImageButton) findViewById(R.id.OtchetButton);
        ShablonBut = (ImageButton) findViewById(R.id.ShablonButton);
        DopolnBut = (ImageButton) findViewById(R.id.DopButton);
        cal = (ConstraintLayout) findViewById(R.id.cal_show_hide);
        otch = (ConstraintLayout) findViewById(R.id.otch_show_hide);
        sha = (ConstraintLayout) findViewById(R.id.shab_show_hide);
        dop = (ConstraintLayout) findViewById(R.id.dop_show_hide);
        GenerateBut = (Button) findViewById(R.id.GenerateBut);

        Integer Code_sotr1 = 0;
        NameShow1 = (TextView) findViewById(R.id.NameShow1);
        NameShow2 = (TextView) findViewById(R.id.NameShow2);
        NameShow3 = (TextView) findViewById(R.id.NameShow3);

        AddWorker = (Button) findViewById(R.id.razrabBut);

        CheckWorkDaysBut = (Button) findViewById(R.id.razrabBut);

        dbHelperMain = new DBHelper(this);
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();

        sw1 = (SwitchCompat) findViewById(R.id.switch1);
        sw2 = (SwitchCompat) findViewById(R.id.switch2);
        sw3 = (SwitchCompat) findViewById(R.id.switch3);
        sw4 = (SwitchCompat) findViewById(R.id.switch4);
        sw5 = (SwitchCompat) findViewById(R.id.switch5);
        sw6 = (SwitchCompat) findViewById(R.id.switch6);
        sw7 = (SwitchCompat) findViewById(R.id.switch7);
        SendPol = (Button) findViewById(R.id.SendPol);
        CommentPozhel = (EditText) findViewById(R.id.CommentPozhel);
        checkPozhel = (Button) findViewById(R.id.holidaysBut);

        addDolzhnost = (Button) findViewById(R.id.FormClockBut);

        addStavka = (Button) findViewById(R.id.politicsBut);

        DaysOfWeek = (Button) findViewById(R.id.DaysOfWeekBut);

        RaspisanieBut = (Button) findViewById(R.id.RaspisanieBut);

        showRaspsisanie = (Spinner) findViewById(R.id.spinnerscrolla);
        textdlya = (TextView) findViewById(R.id.textdlya);
        textdlya2 = (TextView) findViewById(R.id.textdlya2);
        textdlya3 = (TextView) findViewById(R.id.textdlya3);
        textdlya4 = (TextView) findViewById(R.id.textdlya4);
        textdlya5 = (TextView) findViewById(R.id.textdlya5);
        textdlya6 = (TextView) findViewById(R.id.textdlya6);
        textdlya7 = (TextView) findViewById(R.id.textdlya7);
        textdlya8 = (TextView) findViewById(R.id.textdlya8);
        textdlya9 = (TextView) findViewById(R.id.textdlya9);
        textdlya10 = (TextView) findViewById(R.id.textdlya10);
        textdlya11 = (TextView) findViewById(R.id.textdlya11);
        textdlya12 = (TextView) findViewById(R.id.textdlya12);

        dataShow = (TextView) findViewById(R.id.dataShow);
        String[] dataShowArr = new String[1];

        pastWeek = (Button) findViewById(R.id.pastWeek);
        nextWeek = (Button) findViewById(R.id.nextWeek);

        // ДЛЯ ОТКРЫТИЯ ВКЛАДКИ С ВВОДОМ ПОЖЕЛАНИЙ
        String[] pozhel_days = new String[] {"пятница", "четверг", "воскресенье"};

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] week_days;
        week_days=getResources().getStringArray(R.array.catNames);

        String[] data_days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            data_days[i] = format.format(calendar1.getTime());
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        pastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar1.add(Calendar.DATE, -14);
                for (int i = 0; i < 7; i++)
                {
                    data_days[i] = format.format(calendar1.getTime());
                    calendar1.add(Calendar.DAY_OF_MONTH, 1);
                }
                Toast toast = Toast.makeText(MainActivity.this, "Установлена неделя: " + data_days[0] + " - " + data_days[6], Toast.LENGTH_LONG);
                toast.show();
                Log.d("days", data_days[0] + " " + data_days[1] + " " + data_days[2] + " " +  data_days[3] + " " +  data_days[4] + " " +  data_days[5] + " " + data_days[6]);
            }
        });

        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 7; i++)
                {
                    data_days[i] = format.format(calendar1.getTime());
                    calendar1.add(Calendar.DAY_OF_MONTH, 1);
                }
                Toast toast = Toast.makeText(MainActivity.this, "Установлена неделя: " + data_days[0] + " - " + data_days[6], Toast.LENGTH_LONG);
                toast.show();
                Log.d("days", data_days[0] + " " + data_days[1] + " " + data_days[2] + " " +  data_days[3] + " " +  data_days[4] + " " +  data_days[5] + " " + data_days[6]);
            }
        });

        //Log.d("current", dayOfTheWeek);
        Log.d("days", data_days[0] + " " + data_days[1] + " " + data_days[2] + " " +  data_days[3] + " " +  data_days[4] + " " +  data_days[5] + " " + data_days[6]);
        //Log.d("week_days", week_days[0] + " " + week_days[1] + " " + week_days[2] + " " + week_days[3] + " " + week_days[4] + " " + week_days[5] + " " + week_days[6]);

        BackRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // КОМАНДЫ ДЛЯ КНОПОК ДЛЯ ПЕРЕХОДА МЕЖДУ РАЗДЕЛАМИ
        OtchetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.setVisibility(View.GONE);
                otch.setVisibility(View.VISIBLE);
                sha.setVisibility(View.GONE);
                dop.setVisibility(View.GONE);
            }
        });
        CalendarBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.setVisibility(View.VISIBLE);
                otch.setVisibility(View.GONE);
                sha.setVisibility(View.GONE);
                dop.setVisibility(View.GONE);

            }
        });
        ShablonBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int PozhelNum = 0;
                for (int i = 0; i < pozhel_days.length; i += 1) {
                    if (pozhel_days[i].equals(dayOfTheWeek)) {
                        cal.setVisibility(View.GONE);
                        otch.setVisibility(View.GONE);
                        sha.setVisibility(View.VISIBLE);
                        dop.setVisibility(View.GONE);
                        PozhelNum += 1;
                    }
                }
                if (PozhelNum == 0) {
                    Toast toast = Toast.makeText(MainActivity.this,  "Пожелания заполняются с ЧТ по ПТ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        DopolnBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.setVisibility(View.GONE);
                otch.setVisibility(View.GONE);
                sha.setVisibility(View.GONE);
                dop.setVisibility(View.VISIBLE);
            }
        });

        // ОТОБРАЖЕНИЕ ИМЕНИ ВОШЕДШЕГО СОТРУДНИКА
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            Code_sotr1 = (Integer) arguments.get("code_sotrudnika");
        }
        String[] columns = new String[]{"Name"};
        String selection = "Code_worker = ?";
        String[] selectionArgs = new String[]{String.valueOf(Code_sotr1)};

        Cursor c = database.query(DBHelper.WORKERS, columns, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            int nameInd = c.getColumnIndex(DBHelper.Name_worker);
            do {
                String n = c.getString(nameInd);
                NameShow1.setText(n);
                NameShow2.setText(n);
                NameShow3.setText(n);
                Toast toast = Toast.makeText(MainActivity.this, "Здравствуйте, " + n, Toast.LENGTH_SHORT);
                toast.show();
            } while (c.moveToNext());
        }

        // TextView KolSmens, KolHours1, KolHours2, KolHoursChill, KolStavka, KolMoney
        KolSmens = (TextView) findViewById(R.id.KolSmens);
        KolHours1 = (TextView) findViewById(R.id.KolHours1);
        KolHours2 = (TextView) findViewById(R.id.KolHours2);
        KolHoursChill = (TextView) findViewById(R.id.KolHoursChill);
        KolStavka = (TextView) findViewById(R.id.KolStavka);
        KolMoney = (TextView) findViewById(R.id.KolMoney);
        OtchetMonthSpinner = (Spinner) findViewById(R.id.spinnerscrollaMonth);
        OtchetYear = (EditText) findViewById(R.id.OtchetYear);
        String[] MonthsArr = new String[] {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        Integer finalCode_sotr = Code_sotr1;
        GenerateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Month = OtchetMonthSpinner.getSelectedItem().toString();
                String Year = OtchetYear.getText().toString();
                int KOL_SMEN = 0;

                if (Year.equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this,  "Укажите год", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    int perebor = 0;
                    while (perebor < MonthsArr.length) {
                        if (MonthsArr[perebor].equals(Month)) {
                            perebor += 1;
                            break;
                        }
                        else {
                            perebor += 1;
                        }
                    }
                    String pereborString;
                    if (perebor < 10) {
                        pereborString = "0" + perebor;
                    } else { pereborString = String.valueOf(perebor);}

                    Log.d("perebor", pereborString);
                    String forStavkaDate = "01." + pereborString + "." + Year;
                    Log.d("forStavkDate", forStavkaDate);

                    String selection = "Code_sotr_rasp = ?";
                    Log.d("code_sotr", String.valueOf(finalCode_sotr));
                    String[] selectionsArgs = new String[] { String.valueOf(finalCode_sotr)};
                    float AllTime = 0;
                    int breakSecondCursor = 0;
                    String finalStavka = "";

                    Cursor otchetF = database.query(DBHelper.RASP, null, selection, selectionsArgs, null, null, null);
                    if (otchetF.moveToFirst()) {
                        int codeIndex = otchetF.getColumnIndex(DBHelper.CodeSotr_rasp);
                        int dataIndex = otchetF.getColumnIndex(DBHelper.Data_rasp);
                        int startTimeIndex = otchetF.getColumnIndex(DBHelper.Start_rasp);
                        int endTimeIndex = otchetF.getColumnIndex(DBHelper.End_rasp);
                        do {
                            int codeSotr = otchetF.getInt(codeIndex);
                            Log.d("otchetF", otchetF.getInt(codeIndex) + " " + String.valueOf((pereborString + "." + Year) + " = " +(otchetF.getString(dataIndex)).substring(3, 10)));
                            if ((pereborString + "." + Year).equals((otchetF.getString(dataIndex)).substring(3, 10))) {
                                KOL_SMEN += 1;
                                String[] StartSplit = (otchetF.getString(startTimeIndex)).split(":");
                                String[] EndSplit = (otchetF.getString(endTimeIndex)).split(":");
                                float intDayStart;
                                float intDayEnd;
                                if (StartSplit[1].equals("30")) {
                                    intDayStart = (float) (Integer.parseInt(StartSplit[0]) + 0.5);
                                } else { intDayStart = (float) (Integer.parseInt(StartSplit[0]));}
                                if (EndSplit[1].equals("30")) {
                                    intDayEnd = (float) (Integer.parseInt(EndSplit[0]) + 0.5);
                                } else { intDayEnd = (float) (Integer.parseInt(EndSplit[0]));}
                                AllTime += intDayEnd - intDayStart;
                                Log.d("floatSt", intDayStart + " - " + intDayEnd);
                                while (breakSecondCursor < 1) {
                                    Log.d("BREAKTAM", "da");
                                    String selection2 = "Code_worker = ?";
                                    String[] selectionArgs2 = new String[] { String.valueOf(codeSotr)};

                                    Cursor takeDol = database.query(DBHelper.WORKERS, null, selection2, selectionArgs2, null, null, null);
                                    if (takeDol.moveToFirst()) {
                                        int DolzhIndex = takeDol.getColumnIndex(DBHelper.Code_dolzh);

                                        do {
                                            int forSeaStav = takeDol.getInt(DolzhIndex);
                                            String selection3 = "Dolzh_stavki = ?";
                                            String[] selectionArgs3 = new String[] {String.valueOf(forSeaStav)};
                                            Log.d("takeDol", "da");

                                            String perem = "";
                                            ArrayList<String> dates = new ArrayList<>();
                                            ArrayList<Integer> stavkas = new ArrayList<>();

                                            Cursor takeStav = database.query(DBHelper.STAVKA, null, selection3, selectionArgs3, null, null, null);
                                            if (takeStav.moveToFirst()) {
                                                int codeDolzh = takeStav.getColumnIndex(DBHelper.Dolzh_stavki);
                                                int codeData = takeStav.getColumnIndex(DBHelper.Data_stavki);
                                                int codeSumStav = takeStav.getColumnIndex(DBHelper.Sum_stavki);
                                                Log.d("takeStav", "da");
                                                do {
                                                    String tableData = takeStav.getString(codeData);
                                                    //Log.d("tableData", tableData);
                                                    dates.add(takeStav.getString(codeData));
                                                    stavkas.add(takeStav.getInt(codeSumStav));
                                                } while (takeStav.moveToNext());
                                            }

                                            //Log.d("datas", dates.get(0) + " " + dates.get(1));
                                            //Log.d("stavkas", stavkas.get(0) + " " + stavkas.get(1));

                                            if (dates.contains(forStavkaDate)) {
                                                finalStavka = String.valueOf(stavkas.get(dates.indexOf(forStavkaDate)));
                                            }
                                            else {
                                                while (!dates.contains(forStavkaDate)) {
                                                    String[] splitDate = forStavkaDate.split("\\.");
                                                       Log.d("splitDate", splitDate[1]);
                                                       if (String.valueOf(splitDate[1].charAt(0)).equals("0")) {
                                                           splitDate[1] = "0" + String.valueOf(Integer.parseInt(String.valueOf(splitDate[1].charAt(1))) - 1);
                                                       } else {
                                                           int qwer = Integer.parseInt(String.valueOf(splitDate[1])) - 1;
                                                          if (qwer < 10) {
                                                              splitDate[1] = "0" + qwer;
                                                          } else {
                                                              splitDate[1] = String.valueOf(qwer);
                                                          }
                                                      }
                                                           forStavkaDate = splitDate[0] +"."+ splitDate[1] +"."+ splitDate[2];
                                                           Log.d("SPLIT", forStavkaDate);
                                                }
                                                finalStavka = String.valueOf(stavkas.get(dates.indexOf(forStavkaDate)));
                                            }
                                        } while (takeDol.moveToNext());
                                    }
                                    breakSecondCursor += 1;
                                }
                            }
                        } while (otchetF.moveToNext());
                    }
                    Log.d("stavka", forStavkaDate);
                    KolMoney.setText(String.valueOf(Integer.parseInt(finalStavka) * (AllTime - KOL_SMEN)));
                    KolHours1.setText(String.valueOf(AllTime - KOL_SMEN));
                    KolHours2.setText(String.valueOf(AllTime - KOL_SMEN));
                    KolSmens.setText(String.valueOf(KOL_SMEN));
                    KolStavka.setText(finalStavka);
                    KolHoursChill.setText(String.valueOf(KOL_SMEN));
                }
            }
        });


        AddWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWorkerActivity.class);
                startActivity(intent);
            }
        });





        // ФУНКЦИОНАЛ СВИТЧЕЙ
        CompoundButton.OnCheckedChangeListener swsw = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("sw", String.valueOf(isChecked));
            }
        };

        // ОТДЕЛЬНЫЙ ДАТАВЫБОР
        DateFormat format2 = new SimpleDateFormat("dd.MM");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setFirstDayOfWeek(Calendar.MONDAY);
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.add(Calendar.DATE, 7);
        String[] data_days_fu = new String[7];
        for (int i = 0; i < 7; i++)
        {
            data_days_fu[i] = format2.format(calendar2.getTime());
            calendar2.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d("days_fu", data_days_fu[0] + " " + data_days_fu[1] + " " + data_days_fu[2] + " " +  data_days_fu[3] + " " +
                data_days_fu[4] + " " +  data_days_fu[5] + " " + data_days_fu[6]);
        sw1.append(" " + data_days_fu[0]);
        sw2.append(" " + data_days_fu[1]);
        sw3.append(" " + data_days_fu[2]);
        sw4.append(" " + data_days_fu[3]);
        sw5.append(" " + data_days_fu[4]);
        sw6.append(" " + data_days_fu[5]);
        sw7.append(" " + data_days_fu[6]);

        sw1.setOnCheckedChangeListener(swsw);
        sw2.setOnCheckedChangeListener(swsw);
        sw3.setOnCheckedChangeListener(swsw);
        sw4.setOnCheckedChangeListener(swsw);
        sw5.setOnCheckedChangeListener(swsw);
        sw6.setOnCheckedChangeListener(swsw);
        sw7.setOnCheckedChangeListener(swsw);

        SendPol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String days = "";
                if (sw1.isChecked()) {
                    days += sw1.getText() + " ";
                }
                if (sw2.isChecked()) {
                    days += sw2.getText() + " ";
                }
                if (sw3.isChecked()) {
                    days += sw3.getText() + " ";
                }
                if (sw4.isChecked()) {
                    days += sw4.getText() + " ";
                }
                if (sw5.isChecked()) {
                    days += sw5.getText() + " ";
                }
                if (sw6.isChecked()) {
                    days += sw6.getText() + " ";
                }
                if (sw7.isChecked()) {
                    days += sw7.getText() + " ";
                }
                String comments = String.valueOf(CommentPozhel.getText());
                Log.d("days", days);
                ContentValues cc = new ContentValues();
                cc.put(DBHelper.Code_sotr, finalCode_sotr);
                cc.put(DBHelper.Wanted_days, days);
                cc.put(DBHelper.Comments, comments);
                database.insert(DBHelper.WISHES, null, cc);
            }
        });

        checkPozhel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = database.query(DBHelper.WISHES, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    int idIndex = c.getColumnIndex(DBHelper.ID_pozhel);
                    int codeIndex = c.getColumnIndex(DBHelper.Code_sotr);
                    int daysIndex = c.getColumnIndex(DBHelper.Wanted_days);
                    int commIndex = c.getColumnIndex(DBHelper.Comments);

                    do {
                        Log.d("WISHES", c.getInt(idIndex) + " " + c.getInt(codeIndex) + " " + c.getString(daysIndex) + " " + c.getString(commIndex));
                    } while (c.moveToNext());
                }
            }
        });

        addDolzhnost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DolzhnostActivity.class);
                startActivity(intent);
            }
        });

        addStavka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StavkaActivity.class);
                startActivity(intent);
            }
        });

        DaysOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DaysOfWeek_Activity.class);
                startActivity(intent);
            }
        });

        RaspisanieBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RaspisanieActivity.class);
                startActivity(intent);
            }
        });


        String[] head = new String[] {"Директор", "Менеджер"};
        String[] off = new String[] {"Официант", "Офик"};
        String[] barmen = new String[] {"Бармен"};
        String[] host = new String[] {"Хостес"};
        String[] shef = new String[] {"Шеф-повар"};
        String[] povar = new String[] {"Повар ГЦ", "Повар ХЦ", "Повар СБ", "Повар"};

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textdlya.setText("");
                textdlya2.setText("");
                textdlya3.setText("");
                textdlya4.setText("");
                textdlya5.setText("");
                textdlya6.setText("");
                textdlya7.setText("");
                textdlya8.setText("");
                textdlya9.setText("");
                textdlya10.setText("");
                textdlya11.setText("");
                textdlya12.setText("");
                String imyadolzh = "";
                String day = showRaspsisanie.getSelectedItem().toString();
                Log.d("daySpin", day);
                int q = 0;
                while (q < week_days.length) {
                    if (week_days[q].equals(day)) {
                        dataShow.setText(data_days[q].substring(0, 5));
                        dataShowArr[0] = data_days[q];
                        break;
                    }
                    else { q += 1; }
                }

                Log.d("datashow", dataShowArr[0]);

                int dayNum = 0;
                String selection = "Name_day = ?";
                String[] selectionArgs = new String[]{day};
                Cursor show = database.query(DBHelper.WEEK, null, selection, selectionArgs, null, null, null);
                if (show.moveToFirst()) {
                    int idDayIndex = show.getColumnIndex(DBHelper.ID_day);

                    do {
                        dayNum = show.getInt(idDayIndex);
                    } while (show.moveToNext());
                }

                String selection1 = "Day_of_rasp = ? and Data_rasp = ?";
                String[] selectionArgs1 = new String[]{String.valueOf(dayNum), dataShowArr[0]};
                String groupBy = "Data_rasp";
                String having = "Data_rasp = " + dataShowArr[0];
                Cursor CS = database.query(DBHelper.RASP, null, selection1, selectionArgs1, null, null, null);
                if (CS.moveToFirst()) {
                    int codeSotrIndex = CS.getColumnIndex(DBHelper.CodeSotr_rasp);
                    int StartTimeIndex = CS.getColumnIndex(DBHelper.Start_rasp);
                    int EndTimeIndex = CS.getColumnIndex(DBHelper.End_rasp);

                    do {
                        String nameSotr = "";
                        int codeSotr = 0;
                        String selection2 = "Code_worker = ?";
                        String[] selectionArgs2 = new String[]{String.valueOf(CS.getInt(codeSotrIndex))};

                        Cursor css = database.query(DBHelper.WORKERS, null, selection2, selectionArgs2, null, null, null);
                        if (css.moveToFirst()) {
                            int nameIndex = css.getColumnIndex(DBHelper.Name_worker);
                            int codeDolzhIndex = css.getColumnIndex(DBHelper.Code_dolzh);

                            do {
                                nameSotr = css.getString(nameIndex);
                                codeSotr = css.getInt(codeDolzhIndex);

                                String selection3 = "_id = ?";
                                String[] selectionArgs3 = new String[] { String.valueOf(codeSotr) };
                                Cursor csDolzh = database.query(DBHelper.DOLZHNOSTI, null, selection3, selectionArgs3, null, null, null);
                                if (csDolzh.moveToFirst()) {
                                    int nameDolzhIndex = csDolzh.getColumnIndex(DBHelper.Name_dolzh);

                                    do {
                                        imyadolzh = csDolzh.getString(nameDolzhIndex);
                                        Log.d("imyaD", imyadolzh);
                                    } while (csDolzh.moveToNext());
                                }
                            } while (css.moveToNext());
                        }
                        for (int i = 0; i < head.length; i+= 1) {
                            if (head[i].equals(imyadolzh)) {
                                textdlya.append(nameSotr + "\n");
                                textdlya2.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");

                                String allText = String.valueOf(textdlya.getText());

                                String findText = String.valueOf(NameShow1.getText());

                                if (allText.contains(findText)) {
                                    int firstIndex = allText.indexOf(findText);
                                    int lastIndex = allText.indexOf(findText) + findText.length();
                                    Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                    final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                    final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                    sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                    textdlya.setText(sb);
                                }
                            }
                        }

                        if (barmen[0].equals(imyadolzh)) {
                            textdlya3.append(nameSotr + "\n");
                            textdlya4.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");

                            String allText = String.valueOf(textdlya3.getText());

                            String findText = String.valueOf(NameShow1.getText());

                            if (allText.contains(findText)) {
                                int firstIndex = allText.indexOf(findText);
                                int lastIndex = allText.indexOf(findText) + findText.length();
                                Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                textdlya3.setText(sb);
                            }
                        }
                        if (host[0].equals(imyadolzh)) {
                            textdlya5.append(nameSotr + "\n");
                            textdlya6.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");

                            String allText = String.valueOf(textdlya5.getText());

                            String findText = String.valueOf(NameShow1.getText());

                            if (allText.contains(findText)) {
                                int firstIndex = allText.indexOf(findText);
                                int lastIndex = allText.indexOf(findText) + findText.length();
                                Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                textdlya5.setText(sb);
                            }
                        }
                        if (off[0].equals(imyadolzh)) {
                            textdlya7.append(nameSotr + "\n");
                            textdlya8.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");
                            Log.d("text7", String.valueOf(textdlya7.getText()));
                            String allText = String.valueOf(textdlya7.getText());

                            String findText = String.valueOf(NameShow1.getText());

                            if (allText.contains(findText)) {
                                int firstIndex = allText.indexOf(findText);
                                int lastIndex = allText.indexOf(findText) + findText.length();
                                Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                textdlya7.setText(sb);
                            }
                        }
                        if (shef[0].equals(imyadolzh)) {
                            textdlya9.append(nameSotr + "\n");
                            textdlya10.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");

                            String allText = String.valueOf(textdlya9.getText());

                            String findText = String.valueOf(NameShow1.getText());

                            if (allText.contains(findText)) {
                                int firstIndex = allText.indexOf(findText);
                                int lastIndex = allText.indexOf(findText) + findText.length();
                                Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                textdlya9.setText(sb);
                            }
                        }
                        if (povar[0].equals(imyadolzh)) {
                            textdlya11.append(nameSotr + "\n");
                            textdlya12.append(CS.getString(StartTimeIndex) + "-" + CS.getString(EndTimeIndex) + "\n");

                            String allText = String.valueOf(textdlya11.getText());

                            String findText = String.valueOf(NameShow1.getText());

                            if (allText.contains(findText)) {
                                int firstIndex = allText.indexOf(findText);
                                int lastIndex = allText.indexOf(findText) + findText.length();
                                Log.d("findName", allText + "\n" + findText + "\n" + firstIndex + " - " + lastIndex);

                                final SpannableStringBuilder sb = new SpannableStringBuilder(allText);

                                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 170, 0));

                                sb.setSpan(fcs, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                textdlya11.setText(sb);
                            }
                        }
                        //textdlya.append(nameSotr + "\n");
                        //textdlya2.append(CS.getString(StartTimeIndex) + " - " + CS.getString(EndTimeIndex) + "\n");
                    } while (CS.moveToNext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        showRaspsisanie.setOnItemSelectedListener(itemSelectedListener);
    }
}



