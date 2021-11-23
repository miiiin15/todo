package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Data.todoList


class todoAdapter(items : ArrayList<todoList>?, context : Context) :
    RecyclerView.Adapter<todoAdapter.ViewHolder?>(){

    var items: ArrayList<todoList>? = items
    var context : Context


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.todo_item,
            viewGroup,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        /*viewHolder.tv_id.text = items!!.get(position).id
        viewHolder.tv_title.text = items!!.get(position).title
        viewHolder.tv_time.text = items!!.get(position).time

        viewHolder.itemView.setOnClickListener{
            Log.e("클릭함 ",position.toString())
            val intent = Intent(context,Contents::class.java)
            *//*intent.putExtra("id",items!!.get(position).id)*//*
            context.startActivity(intent)
        }*/
        viewHolder.bind(items!!.get(position),context)
    }


    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tv_id = itemView.findViewById<TextView>(R.id.tv_id)
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var tv_time = itemView.findViewById<TextView>(R.id.tv_time)

        fun bind(todoList: todoList, context: Context){
            tv_id.text = todoList.id
            tv_title.text = todoList.title
            tv_time.text = todoList.time


        }



    }


    init {
        this.items
        this.context = context
    }

}