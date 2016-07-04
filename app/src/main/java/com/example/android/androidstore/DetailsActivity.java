package com.example.android.androidstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.androidstore.database.DbHelper;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameTextView, priceTextView, qtyTextView;
    Button increaseQtyButton, decreaseQtyButton, contactSupplierButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get any data passed in from Fragment
        Intent detailsIntent = getIntent();
        final String name = detailsIntent.getStringExtra("name");

        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);
        qtyTextView = (TextView) findViewById(R.id.qtyTextView);
        increaseQtyButton = (Button) findViewById(R.id.increaseQtyButton);
        decreaseQtyButton = (Button) findViewById(R.id.decreaseQtyButton);
        contactSupplierButton = (Button) findViewById(R.id.contactSupplierButton);
        deleteButton = (Button) findViewById(R.id.deleteProductButton);


        nameTextView.setText(name);

        final DbHelper helper = new DbHelper(DetailsActivity.this);

        final Product product = helper.getProduct(name);

        if (product != null) {

            priceTextView.setText("$ " + product.getPrice());
            qtyTextView.setText(product.getQuantity() + "");

            Bitmap bitmap = product.getBitmapImage();
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }

            increaseQtyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    helper.increaseQuantity(name);
                    qtyTextView.setText(helper.getProduct(name).getQuantity() + "");
                }
            });

            decreaseQtyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    helper.decreaseQuantity(name);
                    qtyTextView.setText(helper.getProduct(name).getQuantity() + "");
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    helper.deleteProduct(name);
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder ab = new AlertDialog.Builder(DetailsActivity.this);
                    ab.setMessage("Delete " + name + " ?").setPositiveButton("DELETE", dialogClickListener)
                            .setNegativeButton("CANCEL", dialogClickListener).show();
                }
            });


            contactSupplierButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // http://stackoverflow.com/questions/8701634/send-email-intent
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", product.getSupplierEmail(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Order for " + product.getName());
                    startActivity(emailIntent);
                }
            });

        }
    }
}
