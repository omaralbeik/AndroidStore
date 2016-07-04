package com.example.android.androidstore.database;

/**
 *
 * Created by omar on 7/4/16.
 */

public class DbContract {

    public static final String DATABASE_NAME = "android_store.db";
    public static final int DATABASE_VERSION = 1;

    public static String TABLE_NAME = "products";

    public static String COL_ID = "id";
    public static String COL_NAME = "name";
    public static String COL_PRICE = "price";
    public static String COL_QTY = "quantity";
    public static String COL_SUPPLIER_NAME = "supplierName";
    public static String COL_SUPPLIER_EMAIL = "supplierEmail";
    public static String COL_IMAGE = "image";

    public static String TYPE_ID = "INTEGER PRIMARY KEY";
    public static String TYPE_NAME = "TEXT NOT NULL";
    public static String TYPE_PRICE = "REAL NOT NULL";
    public static String TYPE_QTY = "INTEGER NOT NULL";
    public static String TYPE_SUPPLIER_NAME = "TEXT NOT NULL";
    public static String TYPE_SUPPLIER_EMAIL = "TEXT NOT NULL";
    public static String TYPE_IMAGE = "BLOB";

    public static String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID +                " " + TYPE_ID + ", " +
            COL_NAME +              " " + TYPE_NAME + ", " +
            COL_PRICE +             " " + TYPE_PRICE + ", " +
            COL_QTY +               " " + TYPE_QTY + ", " +
            COL_SUPPLIER_NAME +     " " + TYPE_SUPPLIER_NAME + ", " +
            COL_SUPPLIER_EMAIL +    " " + TYPE_SUPPLIER_EMAIL + ", " +
            COL_IMAGE +        " " + TYPE_IMAGE +
            ");";

    public static String TABLE_CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COL_ID +                " " + TYPE_ID + ", " +
            COL_NAME +              " " + TYPE_NAME + ", " +
            COL_PRICE +             " " + TYPE_PRICE + ", " +
            COL_QTY +               " " + TYPE_QTY + ", " +
            COL_SUPPLIER_NAME +     " " + TYPE_SUPPLIER_NAME + ", " +
            COL_SUPPLIER_EMAIL +    " " + TYPE_SUPPLIER_EMAIL + ", " +
            COL_IMAGE +        " " + TYPE_IMAGE +
            ");";

    public static String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
