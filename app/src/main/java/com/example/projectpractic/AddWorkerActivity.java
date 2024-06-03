package com.example.projectpractic;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class AddWorkerActivity extends AppCompatActivity {

    Button backBut;
    Button addBut;
    Button checkBut;
    EditText Name, Code, Dolzh;
    DBHelper dbHelperMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_worker_page);

        backBut = (Button) findViewById(R.id.workerBackBut);
        addBut = (Button) findViewById(R.id.workerAddBut);
        checkBut = (Button) findViewById(R.id.workerCheckBut);

        Name = (EditText) findViewById(R.id.workerName);
        Code = (EditText) findViewById(R.id.workerId);
        Dolzh = (EditText) findViewById(R.id.workerDolzh);

        dbHelperMain = new DBHelper(this);
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();



        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cAdd = new ContentValues();
                String AddName = String.valueOf(Name.getText());
                Integer AddCode = Integer.parseInt(String.valueOf(Code.getText()));

                Integer Add_Dolzh_Final = 0;
                String AddDolzh = String.valueOf(Dolzh.getText());
                String[] columns = new String[] {"_id"};
                String selection = "Name_dolzh = ?";
                String[] selectionArgs = new String[] { AddDolzh };
                Cursor get_id_dolzh = database.query(DBHelper.DOLZHNOSTI, columns, selection, selectionArgs, null, null, null);
                if (get_id_dolzh.moveToFirst()) {
                    int IDIndex = get_id_dolzh.getColumnIndex(DBHelper.ID_dolzh);

                    do {
                        Add_Dolzh_Final = get_id_dolzh.getInt(IDIndex);
                    } while (get_id_dolzh.moveToNext());
                }

                if (Add_Dolzh_Final > 0) {
                    Log.d("check", AddName + " " + AddCode);
                    cAdd.put(DBHelper.Name_worker, AddName);
                    cAdd.put(DBHelper.Code_dolzh, Add_Dolzh_Final);
                    cAdd.put(DBHelper.ID_worker, AddCode);
                    database.insert(DBHelper.WORKERS, null, cAdd);
                }
                else {
                    Toast toast = Toast.makeText(AddWorkerActivity.this, "Такой должности не существует", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = database.query(DBHelper.WORKERS, null, null, null, null, null, null);
                if (c.moveToFirst()){
                    int idIndex = c.getColumnIndex(DBHelper.ID_worker);
                    int nameIndex = c.getColumnIndex(DBHelper.Name_worker);
                    int dolzhIndex = c.getColumnIndex(DBHelper.Code_dolzh);

                    do {
                        Log.d("Worker", c.getInt(idIndex) + " " + c.getString(nameIndex) + " " + c.getInt(dolzhIndex));
                    } while (c.moveToNext());
                }
            }
        });


    }
}
