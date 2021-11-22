package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.DBHelper
import com.example.todo.Data.todoList

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase
    lateinit var layoutmanager : LinearLayoutManager
    lateinit var arraylist : ArrayList<todoList>
    val edt_input : EditText by lazy{
        findViewById(R.id.edt_input)
    }
    val btn_in : Button by lazy{
        findViewById(R.id.btn_in)
    }
    val rv_list : RecyclerView by lazy{
        findViewById(R.id.rv_list)
    }

    @SuppressLint("Range")
    fun getAll(){
        arraylist = ArrayList<todoList>()
        arraylist.clear()
        var query = "SELECT * FROM todo;"
        var c = database.rawQuery(query,null)
        while(c.moveToNext()){
            val id = c.getString(c.getColumnIndex("id"))
            val title = c.getString(c.getColumnIndex("title"))
            val contents = c.getString(c.getColumnIndex("contents"))
            val time = c.getString(c.getColumnIndex("time"))
            val todolist = todoList(id,title,contents,time)
            arraylist.add(todolist)
            Log.e("결 과",c.getString(c.getColumnIndex("title"))+"/"+c.getString(c.getColumnIndex("contents"))
            +"/"+c.getString(c.getColumnIndex("time")))
        }

        //todo 어뎁터부터 다시 짜기!

    }

    fun Insert(){
        val text = edt_input.text.toString()

        var contentValues = ContentValues()
        contentValues.put("title",text)
        contentValues.put("contents","test")
        contentValues.put("time",SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()).toString())
        database.insert("todo",null,contentValues)
        getAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHelper = DBHelper(this, "Todo.db", null, 1)
        database = dbHelper.writableDatabase
        dbHelper.onCreate(database)
        rv_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_list.setHasFixedSize(true)


        getAll()



        btn_in.setOnClickListener{
            if(edt_input.text.isEmpty())
                return@setOnClickListener
            Insert()
        }
    }
}