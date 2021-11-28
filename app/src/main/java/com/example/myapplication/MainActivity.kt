package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.todo.DBHelper
import com.example.todo.Data.todoList


import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    lateinit var arraylist : ArrayList<todoList>

    val dbHelper : DBHelper by lazy { DBHelper(this, "Todo.db", null, 1) }
    val database : SQLiteDatabase by lazy { dbHelper.writableDatabase }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper.onCreate(database)

        setReceycleerView(getAll())

        binding.btnIn.setOnClickListener{
            if(binding.edtInput.text.isEmpty()||binding.edtInput.text.indexOf("/")==-1) return@setOnClickListener
            Insert()
            setReceycleerView(getAll())
            binding.edtInput.setText("")
            closeKeyboard()
        }
    }


    fun setReceycleerView(arrayList: ArrayList<todoList>){

        val todoAdapter = todoAdapter(arraylist,this,this)
        binding.rvList.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){
                var index =(binding.rvList.adapter as todoAdapter).getId(viewHolder.adapterPosition)
                database.execSQL("delete from todo where id = ${index}")
                setReceycleerView(getAll())
                //Log.e("삭제 ID " , index.toString())
            }
        }).apply { attachToRecyclerView(binding.rvList) }
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
            //Log.e("결 과",id+"-"+title+"."+contents +"/"+time+"/"+finished_time)
        }
        return arraylist
    }

    fun Insert(){
        val str = binding.edtInput.text.split("/")
        val title = str[0]
        val contents = str[1]
        var contentValues = ContentValues().apply {
            put("title",title)
            put("contents",contents)
            put("time",SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()).toString())
        }
        database.insert("todo",null,contentValues)
    }

    fun closeKeyboard() {
        var view = this.currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        val dlg = CustomDialog(this,this).dialogTwoButton()
    }

}