package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.DBHelper
import com.example.todo.Data.todoList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class todoAdapter(items : ArrayList<todoList>?, context : Context, activity: Activity) :
    RecyclerView.Adapter<todoAdapter.ViewHolder?>(){

    var items: ArrayList<todoList>? = items
    var context : Context = context
    var act = activity

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val inflater: LayoutInflater =  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(R.layout.todo_item, viewGroup, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items!!.get(position),context,act)
    }


    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }


    fun getId(position: Int) : String {
        return items!![position].id
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_finish = itemView.findViewById<CheckBox>(R.id.btn_finish)
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var tv_time = itemView.findViewById<TextView>(R.id.tv_time)


        fun bind(todoList: todoList, context: Context, activity: Activity){
            tv_title.text = todoList.title
            tv_time.text = todoList.time

            if(todoList.finished_time != "not") itemView.setBackgroundColor(Color.parseColor("#cabeb3"))

            btn_finish.setOnClickListener {

                var dbHelper = DBHelper(context, "Todo.db", null, 1)
                var database = dbHelper.writableDatabase

                var sql = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()).toString()
                if(todoList.finished_time!="not"){sql = "not"}

                database.execSQL("update todo set finished_time = '${sql}' where id = '${todoList.id}'; ")

                (activity as MainActivity).setReceycleerView((activity).getAll())
            }

            itemView.setOnClickListener {
                val intent = Intent(context, Contents::class.java).apply {
                    putExtra("id", todoList.id)
                    putExtra("title", todoList.title)
                    putExtra("contents", todoList.contents)
                    putExtra("time", todoList.time)
                }
                context.startActivity(intent)
                activity.finish()
            }

        }
    }
}