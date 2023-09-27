package com.example.sqlitebasededatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GestionBD  extends SQLiteOpenHelper {

    private static final String DATABASE_USERS="dbUsuaurios";
    private static final int VERSION=1;
    private static final String TABLE_USERS="usuarios";

    private static final String CREATE_TABLE_USERS="CREATE TABLE "+TABLE_USERS+" (USU_DOCUMENTO INTEGER PRIMARY KEY, USU_USUARIO Varchar(50) NOT NULL , " +
            " USU_NOMBRES Varchar(150) NOT NULL, USUS_APELLIDOS Varchar(150) NOT NULL, USU_CONTRA Varchar(25) NOT NULL)";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERS;


    public GestionBD(@Nullable Context context) {
        super(context, TABLE_USERS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE_USERS);

        Log.i("BASE DE DATOS","SE CREÒ LA BASE DE DATOS");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);

    }
}
