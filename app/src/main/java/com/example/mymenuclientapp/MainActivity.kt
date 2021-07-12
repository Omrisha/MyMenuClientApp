package com.example.mymenuclientapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenuclientapp.adpaters.UsersAdapter
import com.example.mymenuclientapp.api.ServiceBuilder
import com.example.mymenuclientapp.api.UserService
import com.example.mymenuclientapp.models.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AddUserFragment.SendDataInterface {
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private val users: HashMap<String, UserModel> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRecyclerView = findViewById<RecyclerView>(R.id.user_list)

        val addUserButton = findViewById<FloatingActionButton>(R.id.add_user_button)
        addUserButton.setOnClickListener { view ->
            val addUserFragment = AddUserFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.add_user_fragment, addUserFragment)
                .addToBackStack(addUserFragment.tag)
                .commit()

        }

        if (users.isEmpty()) {
            loadFromCloud()
        } else {
            loadLocally()
        }
    }

    private fun loadFromCloud() {
        val destinationService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = destinationService.getUsers()

        requestCall.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                if (response.isSuccessful) {
                    val usersList = response.body()!!

                    cacheKingdomList(usersList)
                    loadLocally()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Toast.makeText(applicationContext, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadLocally() {
        val usersList = ArrayList(users.values)
        usersAdapter = UsersAdapter(usersList)

        usersRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = usersAdapter
        }

    }

    private fun cacheKingdomList(usersList: List<UserModel>) {
        for (user in usersList) {
            users[user.email] = user
        }
    }

    override fun sendData(data: UserModel?) {
        if (data != null) {
            users[data.email] = data
            usersAdapter.updateData(data)
        }
    }
}