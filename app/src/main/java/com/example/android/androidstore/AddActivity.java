package com.example.android.androidstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidstore.database.DbHelper;

public class AddActivity extends AppCompatActivity {

    private EditText nameEditText, qtyEditText, priceEditText, supplierNameEditText, supplierEmailEditText;
    private Button saveButton;
    private DbHelper sharedDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        sharedDbHelper = new DbHelper(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        
        if (actionBar != null) {
            actionBar.setTitle("New Product");
        }

        saveButton = (Button) findViewById(R.id.saveButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        qtyEditText = (EditText) findViewById(R.id.qtyEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        supplierNameEditText = (EditText) findViewById(R.id.supplierNameEditText);
        supplierEmailEditText = (EditText) findViewById(R.id.supplierEmailEditText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    addProduct();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println(item.getItemId());

        System.out.println(R.id.home);
        System.out.println(R.id.action_save);

        if (item.getItemId() == R.id.action_save) {

            if (validateFields()) {
                addProduct();
                this.finish();
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    private void addProduct() {
        if (validateFields()) {
            String name = nameEditText.getText().toString().trim();
            double price = Double.parseDouble(priceEditText.getText().toString().trim());
            int qty = Integer.parseInt(qtyEditText.getText().toString().trim());
            String supplierName = supplierNameEditText.getText().toString().trim();
            String supplierEmail = supplierEmailEditText.getText().toString().trim();
            sharedDbHelper.insertProduct(name, price, qty, supplierName, supplierEmail, null);
        }
    }

    private boolean validateFields() {

        if (isEmpty(nameEditText)) {
            showToast("Please enter name and try again!");
            return false;

        } else if (sharedDbHelper.getProduct(nameEditText.getText().toString().trim()) != null) {
            // product name has been used before
            showToast(nameEditText.getText().toString().trim() + " is available in the store, try another name!");
            return false;
        }

        if (isEmpty(priceEditText)) {
            showToast("Please enter price and try again!");
            return false;
        }

        if (isEmpty(qtyEditText)) {
            showToast("Please enter quantity and try again!");
            return false;
        }

        if (isEmpty(supplierNameEditText)) {
            showToast("Please enter supplier name and try again!");
            return false;
        }

        if (isEmpty(supplierEmailEditText)) {
            showToast("Please enter supplier email and try again!");
            return false;
        }

        return true;
    }

    private boolean isEmpty(EditText editText) {
        return  (editText.getText().toString().trim().isEmpty());
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
