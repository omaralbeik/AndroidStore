package com.example.android.androidstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.android.androidstore.database.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    private FloatingActionButton addButton;
    private ListView listView;

    DbHelper helper;
    ProductAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.example.android.androidstore", MODE_PRIVATE);

        listView = (ListView) findViewById(R.id.listView);

        updateListView();

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addActivityIntent = new Intent(MainActivity.this, AddActivity.class);
                addActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(addActivityIntent);
            }
        });

    }

    private void updateListView() {
        helper = new DbHelper(MainActivity.this);
        products = new ArrayList<>();
        ArrayList<Product> fetchedProducts = helper.getAllProducts();

        if (fetchedProducts != null) {
            products = fetchedProducts;
        }

        adapter = new ProductAdapter(MainActivity.this, products);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {

            helper = new DbHelper(MainActivity.this);
            helper.populateTextProducts();
            products = helper.getAllProducts();
            adapter = new ProductAdapter(MainActivity.this, products);
            listView.setAdapter(adapter);

            prefs.edit().putBoolean("firstRun", false).commit();
        }

    }
}
