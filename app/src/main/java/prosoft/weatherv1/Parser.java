package prosoft.weatherv1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


/**
 * Created by Burol on 24/11/2015.
 */
public class Parser {


    ////////////////////////////////

    //////////////////////

    ////Methods/////////
    /**
     This method build URL with city name
     @param CityName - City Name
     @return the value returned by the method - String with URL
     */
    public String CreateCityURL(String CityName)
    {
        return "@http://api.openweathermap.org/data/2.5/weather?q="+ CityName +"&units=metric&appid=2de143494c0b295cca9337e1e96b00e0";
    }
    /**
     This method build URL with city name + unit eg. metric
     @param CityName - City Name
     @param unit - Unit name eg metric
     @return the value returned by the method - String with full URL
     */
    public String CreateCityURL(String CityName, String unit)
    {
        if(unit.isEmpty())
            unit = "metric"; // default metric if provided is empty
        return "@http://api.openweathermap.org/data/2.5/weather?q="+ CityName +"&" +unit+"=metric&appid=2de143494c0b295cca9337e1e96b00e0";
    }
    public String GetSource(String url)
    {
        String result = "";// empty string
        try {
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost); //
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity); // save the feed to string
                //Catch exceptions
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // return Feed
            return result;

        } catch (Exception e) {
        }
        return result;
    }
    public void ParseWeather(String Feed)
    {
        final String TAG_COORDS = "coord";
        final String TAG_LON = "coord";
        final String TAG_LAT = "coord";
        final String TAG_WEATHER = "coord";
        final String TAG_WEATHER_ID = "coord";
        final String TAG_WEATHER_MAIN = "coord";
        final String TAG_WEATHER_DESCRIPTION = "coord";
        final String TAG_WEATHER_ICON = "coord";
        final String TAG_MAIN_TEMP = "coord";
        final String TAG_WEED_SPEED = "coord";

        /**
        if (Feed != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String email = c.getString(TAG_EMAIL);
                    String address = c.getString(TAG_ADDRESS);
                    String gender = c.getString(TAG_GENDER);

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject(TAG_PHONE);
                    String mobile = phone.getString(TAG_PHONE_MOBILE);
                    String home = phone.getString(TAG_PHONE_HOME);
                    String office = phone.getString(TAG_PHONE_OFFICE);

                    // tmp hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    contact.put(TAG_ID, id);
                    contact.put(TAG_NAME, name);
                    contact.put(TAG_EMAIL, email);
                    contact.put(TAG_PHONE_MOBILE, mobile);

                    // adding contact to contact list
                    contactList.add(contact);

                }
            }
            catch (Exception e){}
        }
        **/
    }
    public static Bitmap GetImage(WeatherData weatherData) throws IOException {
        /**         URL url = new URL("http://openweathermap.org/img/w/" + weatherData.getID());
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setDoInput(true);
         conn.connect();
         InputStream is = conn.getInputStream();
         return BitmapFactory.decodeStream(is);
         **/
        try {
            URL url = new URL("http://openweathermap.org/img/w/" + weatherData.getIcon());
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.i("download img","I think is downloaded");
            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }
    }
}
