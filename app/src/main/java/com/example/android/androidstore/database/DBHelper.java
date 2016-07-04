package com.example.android.androidstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.androidstore.Product;

import java.util.ArrayList;

/**
 *
 * Created by omar on 7/4/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private ContentValues values;
    public SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
        this.context = context;
        this.values = new ContentValues();
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.TABLE_CREATE_IF_NOT_EXISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DbContract.TABLE_DROP);
        onCreate(sqLiteDatabase);
    }

    public void insertProduct(Product product) {
        insertProduct(product.getName(), product.getPrice(), product.getQuantity(), product.getSupplierName(), product.getSupplierEmail(), product.getImage());
    }



    public void insertProduct(String name, double price, int quantity, String supplierName, String supplierEmail, byte[] image) {

        values.put(DbContract.COL_NAME, name);
        values.put(DbContract.COL_PRICE, price);
        values.put(DbContract.COL_QTY, quantity);
        values.put(DbContract.COL_SUPPLIER_NAME, supplierName);
        values.put(DbContract.COL_SUPPLIER_EMAIL, supplierEmail);
        values.put(DbContract.COL_IMAGE, image);

        db.insert(DbContract.TABLE_NAME, null, values);
    }


    // Delete all products
    public int deleteAllEntries() {
        return db.delete(DbContract.TABLE_NAME, null, null);
    }

    // Delete a product
    public boolean deleteProduct(String name) {
        return db.delete(DbContract.TABLE_NAME, DbContract.COL_NAME + "=?", new String[]{name}) > 0;
    }

    // delete the entire database
    public boolean deleteDatabase(String name) {
        return context.deleteDatabase(DbContract.DATABASE_NAME);
    }

    // update the quantity for a product
    public void updateQuantity(String name, int quantity) {
        String update = "UPDATE " + DbContract.TABLE_NAME + " SET " + DbContract.COL_QTY + " = "
                + (quantity) + " WHERE " + DbContract.COL_NAME + " = \"" + name + "\"";
        db.execSQL(update);
    }

    // get a product from the table
    public Product getProduct(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.TABLE_NAME + " WHERE " +  DbContract.COL_NAME + " = " + "\"" + name + "\";", null);
        cursor.moveToFirst();
        return getProductFromCursor(cursor);
    }


    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Product product = getProductFromCursor(cursor);
            if (product != null) {
                products.add(product);
            }
            cursor.moveToNext();
        }
        return products;
    }


    private Product getProductFromCursor(Cursor cursor) {

        // no product was found, return null
        if (cursor.getCount() == 0) {
            return null;
        }

        String name = cursor.getString(cursor.getColumnIndex(DbContract.COL_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(DbContract.COL_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(DbContract.COL_QTY));
        String supplierName = cursor.getString(cursor.getColumnIndex(DbContract.COL_SUPPLIER_NAME));
        String supplierEmail = cursor.getString(cursor.getColumnIndex(DbContract.COL_SUPPLIER_EMAIL));
        byte[] image = cursor.getBlob(cursor.getColumnIndex(DbContract.COL_IMAGE));
        return new Product(name, price, quantity, supplierName, supplierEmail, image);
    }


}
