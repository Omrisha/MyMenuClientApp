package com.example.mymenuclientapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.MealsModel
import com.example.mymenuclientapp.adpaters.FoodsAdapter
import com.example.mymenuclientapp.adpaters.UsersAdapter
import com.example.mymenuclientapp.api.ServiceBuilder
import com.example.mymenuclientapp.api.UserService
import com.example.mymenuclientapp.models.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodActivity : AppCompatActivity(), AddFoodFragment.SendFoodDataInterface {
    private lateinit var mealsRecyclerView: RecyclerView
    private lateinit var mealsAdapter: FoodsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        mealsRecyclerView = findViewById(R.id.food_list)

        var meals = readAll()
        mealsAdapter = FoodsAdapter(meals)

        mealsRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = mealsAdapter
        }

        val addFoodButton = findViewById<FloatingActionButton>(R.id.add_food_button)
        addFoodButton.setOnClickListener {
            val addFoodFragment = AddFoodFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.add_food_fragment, addFoodFragment)
                .addToBackStack(addFoodFragment.tag)
                .commit()

        }
    }



    private fun readAll() : ArrayList<String>{
        var meals = ArrayList<String>()
        Amplify.DataStore.query(
            MealsModel::class.java,
            { items ->
                while (items.hasNext()) {
                    val item = items.next()
                    meals.add(item.name)
                    Log.i("Amplify", "Queried item: " + item.id)
                }
            },
            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
        )

        return meals
    }

    override fun sendData(data: String) {
        if (data != null) {
            mealsAdapter.updateData(data)
        }
    }
}