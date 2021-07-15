package com.example.mymenuclientapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class EmployeeActivity extends AppCompatActivity {

    String Appid = "mongodbconnection-wueda";
    private Button button;
    private EditText editText;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_employees);
        editText = (EditText) findViewById(R.id.new_employee);
        button = (Button) findViewById(R.id.employee_upload_button);


        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    Log.v("User", "Logged in Anonymously");
                } else {
                    Log.v("User", "Failed to Login ");
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Employee");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Employees");

                mongoCollection.insertOne(new Document("userid", user.getId()).append("data", editText.getText().toString())).getAsync(result -> {
                    Log.v("userid", user.getId());
                    Log.v("data", editText.getText().toString());
                    if (result.isSuccess()) {
                        Log.v("Data", "Data inserted Successfully");

                    } else {
                        Log.v("Data", "Error ");
                    }
                });
            }
        });


    }
}
