package com.yovix.tp07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHandler extends SQLiteOpenHelper {
    private static final String db_name = "TP07.sqlite";
    private static final String table_name = "product";
    private static final String ref = "ref", design = "design", prix = "prix", qty = "qty";
    private static final int version = 1;
    //private static DBHandler db_instance;
    SQLiteDatabase db;
    private Context context;

    public DBHandler(Context ct) {
        super(ct, db_name, null, 1);
    }
/*
    public static synchronized DBHandler getInstance(Context ct) {
        if (db_instance == null) {
            db_instance = new DBHandler(ct);
        }
        return db_instance;
    }
 */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + " ( "
                + ref + " VARCHAR PRIMARY KEY, "
                + design + " VARCHAR,"
                + prix + " FLOAT,"
                + qty + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public void insertProduct(String ref, String design, float prix, int qty) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ref", ref);
        values.put("design", design);
        values.put("prix", prix);
        values.put("qty", qty);
        try {
            db.insert(table_name, null, values);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        db.close();
    }

    public ArrayList<Product> listProduct() {
        db = this.getReadableDatabase();
        ArrayList<Product> res = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + ";", null);
            String ref, design;
            float prix;
            int qty;
            while (cursor.moveToNext()) {
                ref = cursor.getString(0);
                design = cursor.getString(1);
                prix = cursor.getFloat(2);
                qty = cursor.getInt(3);
                res.add(new Product(ref, design, prix, qty));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        db.close();
        return res;
    }

    public void updateProduct(Product p) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("design", p.getDesign());
        values.put("prix", p.getPrix());
        values.put("qty", p.getQty());
        try {
            db.update(table_name, values, ref + "=?", new String[]{p.getRef()});
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        db.close();
    }

    public void deleteProduct(String ref_input) {
        db = this.getWritableDatabase();
        try {
            db.delete(table_name, ref + "=?", new String[]{ref_input});
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        db.close();
    }

    public ArrayList<Product> findProduct(String design_input) {
        db = this.getReadableDatabase();
        ArrayList<Product> res = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + design + " like ?;", new String[]{"%" + design_input + "%"});
            if (cursor.moveToFirst()) {
                do {
                    Product p = new Product();
                    p.setRef(cursor.getString(0));
                    p.setDesign(cursor.getString(1));
                    p.setPrix(Float.parseFloat(cursor.getString(2)));
                    p.setQty(Integer.parseInt(cursor.getString(3)));
                    res.add(p);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        db.close();
        return res;
    }
}
