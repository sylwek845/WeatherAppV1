package prosoft.weatherv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sylwester Zalewski on 10/12/2015.
 */
public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WeatherDB.db";
    public static final String BITMAP_NAME = "bitmap";
    public static final String LAT_NAME = "lat";
    public static final String LONG_NAME = "lon";
    public static final String NAME = "name";
    public static final String TABLE_WEATHER = "weathers";
    private HashMap hp;

    public DB(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_WEATHER +
                        "(id integer primary key, name text,lon text,lat text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(db);
    }
    public void dropDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
    }
    public boolean insertdata  (String name, String lon, String lat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lon", lon);
        contentValues.put("lat", lat);
        db.insert(TABLE_WEATHER, null, contentValues);
        return true;
    }

    /**
     * Get Single object from the database
     * @param id Primary key
     * @return WeatherData object
     */
    public WeatherData getweather(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEATHER, new String[]{"id",
                        NAME, LONG_NAME, LONG_NAME}, "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WeatherData weatherData = new WeatherData();
        weatherData.setCity(cursor.getString(0));
        weatherData.setCoordLon(Double.parseDouble(cursor.getString(1)));
        weatherData.setCoordLon(Double.parseDouble(cursor.getString(2)));
        return weatherData;
    }

    /**
     * Gets number of rows
     * @return int with number of rows
     */
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "weathers");
        return numRows;
    }

    /**
     * Insert data into database
     * @param weatherData object which you want to insert
     */
    void addWeather(WeatherData weatherData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, weatherData.getCity()); // Name
        values.put(LONG_NAME, weatherData.getCoordLon()); // LON
        values.put(LAT_NAME, weatherData.getCoordLat()); // LON

        // Inserting Row
        db.insert(TABLE_WEATHER, null, values);
        db.close(); // Closing database connection
    }
//
//
//    public Integer deleteWdata (Integer id)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("weathers",
//                "id = ? ",
//                new String[] { Integer.toString(id) });
//    }
//    public void deleteWeater(WeatherData weatherData) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_WEATHER, "id" + " = ?",
//                new String[] { String.valueOf(weatherData.getID()) });
//        db.close();
//    }
    // Getting All Weather Datas

    /**
     * Return List of All records of WeatherData from DB
     * @return List<WeatherData>
     */
    public List<WeatherData> getAllWeather() {
        List<WeatherData> weatherList = new ArrayList<WeatherData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WeatherData weatherData = new WeatherData();
                weatherData.setID(cursor.getString(0));
                weatherData.setCity(cursor.getString(1));
                weatherData.setCoordLon(Double.parseDouble(cursor.getString(2)));
                weatherData.setCoordLat(Double.parseDouble(cursor.getString(3)));
                weatherList.add(weatherData);
            } while (cursor.moveToNext());
        }

        // return weather list
        return weatherList;
    }

}
