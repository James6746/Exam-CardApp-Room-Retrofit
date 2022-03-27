package com.example.modul6_exam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modul6_exam.R
import com.example.modul6_exam.model.Responses

class CustomAdapter(val items: ArrayList<Responses>):RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.card_number.setText(item.cardNumber)
        holder.holder_name.setText(item.cardHolder)
        holder.expire_date.setText(item.expiredDate)
        holder.tv_balance.text = null
    }

    override fun getItemCount(): Int {
      return items.size
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card_number: TextView
        val holder_name: TextView
        val expire_date: TextView
        val tv_balance: TextView
        init {
            card_number = view.findViewById(R.id.card_number)
            holder_name = view.findViewById(R.id.holder_name)
            expire_date = view.findViewById(R.id.expire_date)
            tv_balance = view.findViewById(R.id.tv_balance)
        }
    }
}