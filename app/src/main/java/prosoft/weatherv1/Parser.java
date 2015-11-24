package prosoft.weatherv1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


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
