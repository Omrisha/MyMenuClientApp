package com.example.mymenuclientapp.adpaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenuclientapp.R
import com.example.mymenuclientapp.models.UserModel

class FoodsAdapter (private val meals: ArrayList<String>) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName = itemView.findViewById<TextView>(R.id.food_name_text)

        fun bind(meal: String) {
            foodName.text = meal
        }
    }

    public fun updateData(meal: String) {
        meals.add(meal)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(meals[position])
    }

    override fun getItemCount(): Int {
        return meals.size
    }
}