package uyennlp.demo.demosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(name TEXT PRIMARY KEY, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public static boolean isDbExist(Context context) {
        File dbFile = null;
        boolean dbStatus = false;

        try {
            String dbPath = "/data/data/" + context.getPackageName() + "/databases/" + "data.db";
            dbFile = new File(dbPath);

            dbStatus = dbFile.exists();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbFile != null) {
                dbFile = null;
            }
        }

        return dbStatus;
    }

    public boolean insert(String name, String contact, String dob) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("contact", contact);
        values.put("dob", dob);

        long result = db.insert("Users", null, values);

        if (result == -1) {
            return false;
        }
        return true;
    }


    public boolean update(String name, String contact, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM Users WHERE name = ?", new String[] {name});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("contact", contact);
            values.put("dob", dob);

            int result = db.update("Users", values, "name=?", new String[] {name});

            if (result > -1) {
                return true;
            }
        }

        return false;
    }

    public boolean delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Users WHERE name = ?", new String[] {name});
        if (cursor.getCount() > 0) {
            long result = db.delete("Users", "name=?", new String[] {name});

            if (result > -1) {
                return true;
            }
        }

        return false;
    }

    public List<DTO> view() {
        List<DTO> dtos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            String contact = cursor.getString(1);
            String dob = cursor.getString(2);

            dtos.add(new DTO(name, contact, dob));
            cursor.moveToNext();
        }
        cursor.close();

        return dtos;
    }
}
