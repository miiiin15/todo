package com.example.todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    override fun onCreate(db: SQLiteDatabase) {
        var sql : String = "CREATE TABLE if not exists todo (" +
                "id integer primary key autoincrement," +
                "title text," +
                "contents text," +
                "time text," +
                "finished_time text DEFAULT 'not');";

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists todo"

        db.execSQL(sql)
        onCreate(db)
    }

}