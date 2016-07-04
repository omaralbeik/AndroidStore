package com.example.android.androidstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.androidstore.database.DbHelper;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper helper = new DbHelper(MainActivity.this);

        helper.insertProduct("iPhone", 410.0, 10, "Apple inc", "info@apple.com",null);
        helper.insertProduct("iPhone", 320.0, 10, "HTC", "info@htc.com", null);

        helper.updateQuantity("iPhone", 0);

        System.out.println(helper.getProduct("iPhone").toString());

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addActivityIntent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivity(addActivityIntent);
            }
        });


    }
}
