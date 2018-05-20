package com.SQLib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "solvo_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL("DROP TABLE IF EXISTS " + Establecimiento.TABLE_NAME);
        db.execSQL(Establecimiento.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Establecimiento.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }



    public long insertEstablecimiento(String IDEST, String NOMBRE_EST, String ID_SERV, String DIR_EST, String  TELEFONO_EST, String EMAIL_EST,
                                      double LAT_EST, double LONG_EST, String NIV_PRECIO,  float CALIFICACION) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Establecimiento.COLUMN_IDEST, IDEST);
        values.put(Establecimiento.COLUMN_NOMBRE_EST,NOMBRE_EST);
        values.put(Establecimiento.COLUMN_ID_SERV,ID_SERV);
        values.put(Establecimiento.COLUMN_DIR_EST,DIR_EST);
        values.put(Establecimiento.COLUMN_TELEFONO_EST,TELEFONO_EST);
        values.put(Establecimiento.COLUMN_EMAIL_EST,EMAIL_EST);
        values.put("LAT_EST",LAT_EST);
        values.put(Establecimiento.COLUMN_LONG_EST,LONG_EST);
        values.put(Establecimiento.COLUMN_NIV_PRECIO,NIV_PRECIO);
        values.put(Establecimiento.COLUMN_CALIFICACION,CALIFICACION);
        // insert row
        long id = db.insert(Establecimiento.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Establecimiento getEstablecimiento(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Establecimiento.TABLE_NAME,
                new String[]{
                Establecimiento.COLUMN_IDEST, Establecimiento.COLUMN_NOMBRE_EST,
                Establecimiento.COLUMN_ID_SERV,Establecimiento.COLUMN_DIR_EST,
                Establecimiento.COLUMN_TELEFONO_EST, Establecimiento.COLUMN_EMAIL_EST,
                Establecimiento.COLUMN_LAT_EST, Establecimiento.COLUMN_LONG_EST,
                Establecimiento.COLUMN_NIV_PRECIO, Establecimiento.COLUMN_CALIFICACION },
                Establecimiento.COLUMN_IDEST + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Establecimiento establecimiento = new Establecimiento(
                cursor.getString(cursor.getColumnIndex( Establecimiento.COLUMN_IDEST)),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_NOMBRE_EST)),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_ID_SERV)),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_DIR_EST)),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_TELEFONO_EST)),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_EMAIL_EST)),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_LAT_EST))),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_LONG_EST))),
                cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_NIV_PRECIO)),
                Float.parseFloat(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_CALIFICACION)))
        );

        // close the db connection
        cursor.close();

        return establecimiento;
    }

    public List<Establecimiento> getEstablecimientos() {
        List<Establecimiento> establecimientos = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Establecimiento.TABLE_NAME;
        //String borrarQuery = "DROP TABLE IF EXISTS " + Establecimiento.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        establecimientos.clear();
        if (cursor.moveToFirst()) {
            do {
                Establecimiento establecimiento = new Establecimiento();
                establecimiento.setIDEST(cursor.getString(cursor.getColumnIndex( Establecimiento.COLUMN_IDEST)));
                establecimiento.setNOMBRE_EST(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_NOMBRE_EST)));
                establecimiento.setID_SERV(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_ID_SERV)));
                establecimiento.setDIR_EST(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_DIR_EST)));
                establecimiento.setTELEFONO_EST(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_TELEFONO_EST)));
                establecimiento.setEMAIL_EST(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_EMAIL_EST)));
                establecimiento.setLAT_EST(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_LAT_EST))));
                establecimiento.setLONG_EST(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_LONG_EST))));
                establecimiento.setNIV_PRECIO(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_NIV_PRECIO)));
                establecimiento.setCALIFICACION(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Establecimiento.COLUMN_CALIFICACION))));

                establecimientos.add(establecimiento);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return establecimientos;
    }

    public int getEstablecimientosCount() {
        String countQuery = "SELECT  * FROM " + Establecimiento.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateEstablecimiento(Establecimiento establecimiento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Establecimiento.COLUMN_IDEST, establecimiento.getIDEST());

        // updating row
        return db.update(Establecimiento.TABLE_NAME, values, Establecimiento.COLUMN_IDEST + " = ?",
                new String[]{String.valueOf(establecimiento.getIDEST())});
    }

    public void deleteEstablecimiento(Establecimiento establecimiento) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Establecimiento.TABLE_NAME, Establecimiento.COLUMN_IDEST + " = ?",
                new String[]{String.valueOf(establecimiento.getIDEST())});
        db.close();
    }



}
