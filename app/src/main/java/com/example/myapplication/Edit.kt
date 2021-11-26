package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.TodoEditBinding

import com.example.todo.DBHelper

class Edit : AppCompatActivity(){
    private lateinit var binding: TodoEditBinding

    private lateinit var id : String
    private lateinit var title : String
    lateinit var contents : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TodoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id = intent.getStringExtra("id")
        var title = intent.getStringExtra("title")
        var contents = intent.getStringExtra("contents")

        binding.edtEditTitle.setText(title)
        binding.edtEditContents.setText(contents)

        binding.btnEditBack.setOnClickListener { goBack() }

        binding.btnEditFinish.setOnClickListener {
            if(binding.edtEditContents.text.isEmpty()||binding.edtEditTitle.text.isEmpty())
                return@setOnClickListener

            var dbHelper = DBHelper(this, "Todo.db", null, 1)
            var database = dbHelper.writableDatabase
            database.execSQL("update todo set " +
                    "title = '${binding.edtEditTitle.text}', " +
                    "contents = '${binding.edtEditContents.text}'" +
                    "where id = '${id}'; ")
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    fun goBack(){
        val intent = Intent(this, Contents::class.java).apply {
            putExtra("id", intent.getStringExtra("id"))
            putExtra("title", intent.getStringExtra("title"))
            putExtra("contents", intent.getStringExtra("contents"))
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() { goBack() }
}