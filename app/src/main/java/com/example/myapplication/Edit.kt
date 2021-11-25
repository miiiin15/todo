package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.DBHelper

class Edit : AppCompatActivity(){
    val edt_title : EditText by lazy { findViewById(R.id.edt_edit_title) }
    val edt_contents : EditText by lazy { findViewById(R.id.edt_edit_contents) }

    val btn_back : ImageButton by lazy { findViewById(R.id.btn_edit_back) }
    val btn_edit : ImageButton by lazy { findViewById(R.id.btn_edit_finish) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_edit)
        edt_title.setText(intent.getStringExtra("title"))
        edt_contents.setText(intent.getStringExtra("contents"))

        btn_back.setOnClickListener { finish() }
        btn_edit.setOnClickListener {

            if(edt_contents.text.isEmpty()||edt_title.text.isEmpty())return@setOnClickListener

            var dbHelper = DBHelper(this, "Todo.db", null, 1)
            var database = dbHelper.writableDatabase
            database.execSQL("update todo set " +
                    "title = '${edt_title.text}', contents = '${edt_contents.text}'" +
                    "where id = '${intent.getStringExtra("id")}'; ")
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}