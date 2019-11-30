package com.example.recipe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "recipe.db";

    private static final String RECIPE_TABLE = "recipe";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_END_OF_WARRANTY_DATE = "end_of_warranty_date";

    SQLiteDatabase database;

    public RecipeDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RECIPE_TABLE + " ("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " varchar, "
                + COLUMN_END_OF_WARRANTY_DATE + " varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + RECIPE_TABLE);
        onCreate(db);
    }

    public boolean create(Recipe recipe){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, recipe.getName());
        contentValues.put(COLUMN_END_OF_WARRANTY_DATE, recipe.getEndOfWarrantyDate());

        return database.insert(RECIPE_TABLE, null, contentValues) != -1;
    }
}
