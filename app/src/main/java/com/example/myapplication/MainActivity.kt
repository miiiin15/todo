package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
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

    lateinit var arraylist : ArrayList<todoList>

    val dbHelper : DBHelper by lazy { DBHelper(this, "Todo.db", null, 1) }
    val database : SQLiteDatabase by lazy { dbHelper.writableDatabase }

    val edt_input : EditText by lazy{ findViewById(R.id.edt_input) }
    val btn_in : ImageButton by lazy{ findViewById(R.id.btn_in) }
    val rv_list : RecyclerView by lazy{ findViewById(R.id.rv_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper.onCreate(database)

        setReceycleerView(getAll())

        btn_in.setOnClickListener{
            if(edt_input.text.isEmpty()||edt_input.text.indexOf("/")==-1) return@setOnClickListener
            Insert()
            edt_input.setText("")
            setReceycleerView(getAll())
            closeKeyboard()
        }
    }

    fun setReceycleerView(arrayList: ArrayList<todoList>){
        val todoAdapter = todoAdapter(arraylist,this,this)
        rv_list.adapter = todoAdapter
        rv_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_list.setHasFixedSize(true)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){
                var index =(rv_list.adapter as todoAdapter).getId(viewHolder.adapterPosition)
                Log.e("삭제 ID " , index.toString())
                database.execSQL("delete from todo where id = ${index}")
                setReceycleerView(getAll())
            }
        }).apply {
            attachToRecyclerView(rv_list)
        }
    }

    @SuppressLint("Range")
    fun getAll() : ArrayList<todoList>{
        arraylist = ArrayList<todoList>()
        arraylist.clear()
        var query = "SELECT * FROM todo order by finished_time DESC;"
        var c = database.rawQuery(query,null)
        while(c.moveToNext()){
            val id = c.getString(c.getColumnIndex("id"))
            val title = c.getString(c.getColumnIndex("title"))
            val contents = c.getString(c.getColumnIndex("contents"))
            val time = c.getString(c.getColumnIndex("time"))
            val finished_time = c.getString(c.getColumnIndex("finished_time"))
            val todolist = todoList(id,title,contents,time,finished_time)
            arraylist.add(todolist)
//            Log.e("결 과",id+"-"+title+"."+contents +"/"+time+"/"+finished_time)
        }
        return arraylist
    }

    fun Insert(){
        val str = edt_input.text.toString().split("/")
        val title = str[0]
        val contents = str[1]
        var contentValues_todo = ContentValues()
        contentValues_todo.put("title",title)
        contentValues_todo.put("contents",contents)
        contentValues_todo.put("time",SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()).toString())
        database.insert("todo",null,contentValues_todo)
    }

    fun closeKeyboard() {
        var view = this.currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}