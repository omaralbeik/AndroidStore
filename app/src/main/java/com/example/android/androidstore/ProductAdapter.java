package com.example.android.androidstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.androidstore.database.DbHelper;

import java.util.ArrayList;

/**
 * Created by omar on 7/4/16.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Product currentProduct = getItem(position);
        final int currentQty = currentProduct.getQuantity();

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.nameTextView);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.priceTextView);
        final TextView qtyTextView = (TextView) listItemView.findViewById(R.id.qtyTextView);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);


        nameTextView.setText(currentProduct.getName());
        priceTextView.setText("$ " + currentProduct.getPrice());
        qtyTextView.setText(currentProduct.getQuantity() + " Available");

        Bitmap image = currentProduct.getBitmapImage();

        if (image != null) {
            imageView.setImageBitmap(image);
        } else {
            imageView.setVisibility(View.GONE);
        }

        Button buyButton = (Button) listItemView.findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper helper = new DbHelper(getContext());
                helper.decreaseQuantity(currentProduct.getName());
                qtyTextView.setText(helper.getProduct(currentProduct.getName()).getQuantity() + " Available");
            }
        });


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                detailsIntent.putExtra("name", currentProduct.getName());
                context.startActivity(detailsIntent);
            }
        });


        return listItemView;
    }
}
