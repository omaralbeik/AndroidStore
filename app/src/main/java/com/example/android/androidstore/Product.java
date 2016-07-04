package com.example.android.androidstore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 *
 * Created by omar on 7/3/16.
 */

public class Product {

    private String name;
    private double price;
    private int quantity;
    private String supplierName;
    private String supplierEmail;
    private byte[] image;

    /**
     * Create a mew product
     * @param name product name
     * @param price product price
     * @param quantity available quantity
     * @param supplierName supplier name
     * @param supplierEmail supplier email
     */
    public Product(String name, double price, int quantity, String supplierName, String supplierEmail, byte[] image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public byte[] getImage() {
        return image;
    }

    public Bitmap getBitmapImage() {

        if (image == null || image.length == 0) {
            return null;
        }

        return BitmapFactory.decodeByteArray(image , 0, image .length);
    }

    @Override
    public String toString() {
        return "name: " + name + "\t" + "price: " + "\t" +
                "available quantity: " + quantity + "\t" +
                "supplier name: " + supplierName + "\t" + "supplier email: " + supplierEmail;
    }


    public static byte[] encodeBitmap(Bitmap image) {
        // Convert Bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
