package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.DBHelper

class Contents : AppCompatActivity() {
    val tv_title : TextView by lazy{ findViewById(R.id.tv_contents_title) }
    val tv_contents : TextView by lazy{ findViewById(R.id.tv_contents_contents) }
    val btn_back : ImageButton by lazy { findViewById(R.id.btn_contents_back) }
    val btn_edit : ImageButton by lazy { findViewById(R.id.btn_contents_edit) }
    val btn_del : ImageButton by lazy { findViewById(R.id.btn_contents_delete) }

    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_contents)
        val id = intent.getStringExtra("id").toString()

        Log.e("이동",id)

        dbHelper = DBHelper(this, "Todo.db", null, 1)
        database = dbHelper.writableDatabase


        tv_title.text = intent.getStringExtra("title")
        tv_contents.text = intent.getStringExtra("contents")

        btn_back.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        btn_edit.setOnClickListener {
            var intent = Intent(this,Edit::class.java)
            intent.putExtra("id",id)
            intent.putExtra("title",tv_title.text)
            intent.putExtra("contents",tv_contents.text)
            startActivity(intent)
        }

        btn_del.setOnClickListener {
            database.execSQL("delete from todo where id = ${id}")
            startActivity(Intent(this,MainActivity::class.java))
        }


    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }




}