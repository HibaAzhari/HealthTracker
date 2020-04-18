package android.example.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Nutrition.db";
    public static final String TABLE_NAME = "foods_table";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "LABEL";
    public static final String COL_2 = "CALORIES";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + TABLE_NAME + "(" + COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_1 + " TEXT, " + COL_2 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public boolean addFoods(String label, int calories){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, label);
        contentValues.put(COL_2, calories);
        long val = database.insert(TABLE_NAME, null, contentValues);
        if(val == -1) return false;
        return true;
    }

    public Cursor getFoods() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteFood(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "ID = ?", new String[] {String.valueOf(id)});
    }
}