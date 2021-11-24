package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.DBHelper
import com.example.todo.Data.todoList

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val CALL_TODO = 1
    var CALL_FINISHED = 2

    lateinit var dbHelper_todo : DBHelper
    lateinit var database_todo : SQLiteDatabase
    lateinit var arraylist : ArrayList<todoList>

    val edt_input : EditText by lazy{ findViewById(R.id.edt_input) }
    val btn_in : Button by lazy{ findViewById(R.id.btn_in) }
    val rv_list : RecyclerView by lazy{ findViewById(R.id.rv_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper_todo = DBHelper(this, "Todo.db", null, 1)
        database_todo = dbHelper_todo.writableDatabase
        dbHelper_todo.onCreate(database_todo)

        setReceycleerView(getAll())

        btn_in.setOnClickListener{
            if(edt_input.text.isEmpty()) return@setOnClickListener
            Insert()
            edt_input.setText("")
        }
    }

    fun setReceycleerView(arrayList: ArrayList<todoList>){
        val todoAdapter = todoAdapter(arraylist,this,this)
        rv_list.adapter = todoAdapter
        rv_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_list.setHasFixedSize(true)

        /*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){
                (rv_list.adapter as todoAdapter).removeTask(viewHolder.adapterPosition)
            }
        }).apply {
            attachToRecyclerView(rv_list)
        }*/

    }

    @SuppressLint("Range")
    fun getAll() : ArrayList<todoList>{
        arraylist = ArrayList<todoList>()
        arraylist.clear()
        var query = "SELECT * FROM todo;"
        var c = database_todo.rawQuery(query,null)
        while(c.moveToNext()){
            val id = c.getString(c.getColumnIndex("id"))
            val title = c.getString(c.getColumnIndex("title"))
            val contents = c.getString(c.getColumnIndex("contents"))
            val time = c.getString(c.getColumnIndex("time"))
            val finished_time = c.getString(c.getColumnIndex("finished_time"))
            val todolist = todoList(id,title,contents,time,finished_time)
            arraylist.add(todolist)
            Log.e("결 과",id+"-"+title+"."+contents +"/"+time+"/"+finished_time)
        }
        return arraylist
    }

    fun Insert(){
        val text = edt_input.text.toString()

        var contentValues_todo = ContentValues()
        contentValues_todo.put("title",text)
        contentValues_todo.put("contents","test")
        contentValues_todo.put("time",SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()).toString())
        database_todo.insert("todo",null,contentValues_todo)
        setReceycleerView(getAll())
    }


}