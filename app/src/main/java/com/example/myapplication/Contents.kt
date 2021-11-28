package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.TodoContentsBinding
import com.example.todo.DBHelper

class Contents : AppCompatActivity() {

    private lateinit var binding: TodoContentsBinding
    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TodoContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id").toString()

        Log.e("이동",id)

        dbHelper = DBHelper(this, "Todo.db", null, 1)
        database = dbHelper.writableDatabase

        binding.tvContentsTitle.text = intent.getStringExtra("title")
        binding.tvContentsContents.text = intent.getStringExtra("contents")

        binding.btnContentsBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.btnContentsEdit.setOnClickListener {
            var intent = Intent(this,Edit::class.java).apply {
                putExtra("id",id)
                putExtra("title",binding.tvContentsTitle.text)
                putExtra("contents",binding.tvContentsContents.text)
            }
            startActivity(intent)
            finish()
        }

        binding.btnContentsDelete.setOnClickListener {
            database.execSQL("delete from todo where id = ${id}")
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}