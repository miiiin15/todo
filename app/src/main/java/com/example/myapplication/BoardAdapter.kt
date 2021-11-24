package com.example.myapplication

import android.content.Context
import android.view.View
import java.util.ArrayList

//class BoardAdapter(items: ArrayList<BoardItem>?, context: Context) :
//    RecyclerView.Adapter<BoardAdapter.ViewHolder?>() {
//    var items: ArrayList<BoardItem>? = ArrayList<BoardItem>()
//    var context //선택한 activty에 대한 context
//            : Context
//
//    //클릭이벤트처리 정의
//    var listener: OnItemClickListener? = null
//
//    interface OnItemClickListener {
//        fun onItemClick(holder: ViewHolder?, view: View?, position: Int)
//    }
//
//    //null이 아니라면 어댑터에서 관리하는 아이템의 개수를 반환
//    val itemCount: Int
//        get() = if (items != null) items!!.size else 0
//
//    //뷰홀더가 만들어지는 시점에 호출되는 메소드
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
//        val inflater: LayoutInflater =
//            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val itemView: View = inflater.inflate(
//            R.layout.board_item,
//            viewGroup,
//            false
//        ) //viewGroup는 각각의 아이템을 위해서 정의한 xml레이아웃의 최상위 레이아우싱다.
//        return ViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.tv_category.setText(items!![position].getCategory())
//        viewHolder.tv_title.setText(items!![position].getTitle())
//        viewHolder.tv_comment.setText("(" + items!![position].getComents().toString() + ")")
//        viewHolder.tv_writetime.setText(items!![position].getTime())
//        //클릭리스너
//        viewHolder.setOnItemClickListener(listener)
//    }
//
//    //아이템 추가
//    fun addItem(item: BoardItem) {
//        items!!.add(item)
//    }
//
//    //한꺼번에 추가
//    fun addItems(items: ArrayList<BoardItem>?) {
//        this.items = items
//    }
//
//    fun getItem(position: Int): BoardItem {
//        return items!![position]
//    }
//
//    //클릭리스너관련
//    fun setOnItemClickListener(listener: OnItemClickListener?) {
//        this.listener = listener
//    }
//
//    //뷰홀더
//    //뷰를 담아두는 역할 / 뷰에 표시될 데이터를 설정하는 역할
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tv_category: TextView
//        var tv_title: TextView
//        var tv_comment: TextView
//        var tv_writetime: TextView
//        var listenr //클릭이벤트처리관련 변수
//                : OnItemClickListener? = null
//
//        fun setOnItemClickListener(listenr: OnItemClickListener?) {
//            this.listenr = listenr
//        }
//
//        init {
//            tv_category = itemView.findViewById<TextView>(R.id.tv_board_item_category)
//            tv_title = itemView.findViewById<TextView>(R.id.tv_board_item_contents_title)
//            tv_comment = itemView.findViewById<TextView>(R.id.tv_baord_item_comment)
//            tv_writetime = itemView.findViewById<TextView>(R.id.tv_board_item_write_time)
//
//
//            //아이템 클릭이벤트
//            itemView.setOnClickListener {
//                val position: Int = getAdapterPosition()
//                if (listenr != null) {
//                    listenr!!.onItemClick(this@ViewHolder, itemView, position)
//                }
//            }
//        }
//    }
//
//    init {
//        this.items = items
//        this.context = context
//    }
//}