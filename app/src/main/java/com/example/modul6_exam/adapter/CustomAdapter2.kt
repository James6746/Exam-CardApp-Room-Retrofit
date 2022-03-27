package com.example.modul6_exam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modul6_exam.R
import com.example.modul6_exam.model.Card

class CustomAdapter2(val items1: ArrayList<Card>):RecyclerView.Adapter<CustomAdapter2.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items1[position]
        holder.card_number.setText(item.card_number)
        holder.holder_name.setText(item.card_holder)
        holder.expire_date.setText(item.expired_date)
        holder.tv_balance.text = item.is_exsist.toString()
    }

    override fun getItemCount(): Int {
      return items1.size
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