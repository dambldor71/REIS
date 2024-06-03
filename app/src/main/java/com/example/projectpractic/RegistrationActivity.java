package com.example.projectpractic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class RegistrationActivity extends AppCompatActivity {

    Button VhodVSistemy;
    EditText CodeEntry, NameEntry;

    DBHelper dbHelperR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        VhodVSistemy = (Button) findViewById(R.id.VhodBut);
        CodeEntry = (EditText) findViewById(R.id.CodRegistr);
        NameEntry = (EditText) findViewById(R.id.NameRegistr);

        dbHelperR = new DBHelper(this);
        SQLiteDatabase databaseR = dbHelperR.getWritableDatabase();

        VhodVSistemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer[] Codes = new Integer[100];
                String[] Names = new String[100];
                for (int i = 0; i < 99; i += 1) {
                    Codes[i] = 0;
                    Names[i] = "";
                }
                Codes[99] = 1111;
                Names[99] = "Админ";
                int x = 0;
                Cursor C = databaseR.query(DBHelper.WORKERS, null, null,
                        null, null, null, null);
                if (C.moveToFirst()){
                    int codeIndex = C.getColumnIndex(DBHelper.ID_worker);
                    int nameIndex = C.getColumnIndex(DBHelper.Name_worker);
                    do {
                      Codes[x] = C.getInt(codeIndex);
                      Names[x] = C.getString(nameIndex).split(" ")[0];
                      x += 1;
                    } while (C.moveToNext());
                }
                Integer CheckEntry = Integer.parseInt(String.valueOf(CodeEntry.getText()));
                String CheckNameEntry = String.valueOf(NameEntry.getText());
                int NumC = 0;
                int CheckNum = 0;
                while (NumC < Codes.length) {
                    Log.d("check", CheckEntry + " = " + Codes[NumC] + ", " +
                            CheckNameEntry + " = " + Names[NumC]);
                    if (Codes[NumC].equals(CheckEntry) && Names[NumC].equals(CheckNameEntry)) {
                        CheckNum += 1;
                        break;
                    }
                    NumC += 1;
                }
                if (CheckNum > 0) {

                    Intent intent = new Intent(RegistrationActivity.this,
                            MainActivity.class);
                    intent.putExtra("code_sotrudnika", CheckEntry);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(RegistrationActivity.this,
                            "Неверный код или фамилия сотрудника", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
