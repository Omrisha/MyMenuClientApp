package com.example.mymenuclientapp.adpaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenuclientapp.R
import com.example.mymenuclientapp.models.UserModel

class UsersAdapter(private val users: ArrayList<UserModel>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName = itemView.findViewById<TextView>(R.id.name_text)
        val numberOfPeople = itemView.findViewById<TextView>(R.id.number_of_people_text)
        val cellphoneNumber = itemView.findViewById<TextView>(R.id.phone_number_text)

        fun bind(user: UserModel) {
            fullName.text = "${user.lastName}, ${user.firstName}"
            numberOfPeople.text = "Persons: ${user.numberOfPeople}"
            cellphoneNumber.text = user.cellphoneNumber
        }
    }

    public fun updateData(user: UserModel) {
        users.add(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}