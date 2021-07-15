package com.example.mymenuclientapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureAmplify()
        val clients = findViewById<Button>(R.id.clients_button)
        clients.setOnClickListener {
            val intent = Intent(this, ClientsActivity::class.java)
            startActivity(intent)
        }

        val food = findViewById<Button>(R.id.food_button)
        food.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            startActivity(intent)
        }

        val employees = findViewById<Button>(R.id.employees_button)
        employees.setOnClickListener {
            val intent = Intent(this, EmployeeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Log.i("Amplify", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("Amplify", "Could not initialize Amplify", error)
        }
    }
}