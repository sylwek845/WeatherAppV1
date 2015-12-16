package prosoft.weatherv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sylwester Zalewski on 10/12/2015.
 */
public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WeatherDB.db";
    public static final String BITMAP_NAME = "bitmap";
    public static final String LAT_NAME = "lat";
    public static final String LONG_NAME = "lon";
    public static final String NAME = "name";
    private HashMap hp;

    public DB(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table weathers " +
                        "(id integer primary key, name text,lon text,lat text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS weather");
        onCreate(db);
    }

    public boolean insertdata  (String name, String lon, String lat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lon", lon);
        contentValues.put("lat", lat);
        db.insert("weathers", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from weathers where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "weathers");
        return numRows;
    }

    public boolean updatedata (Integer id, String name, String lon, String lat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lon", lon);
        contentValues.put("lat", lat);
        db.update("weathers", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteCdata (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("weathers",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("weathers")));
            res.moveToNext();
        }
        return array_list;
    }

}
