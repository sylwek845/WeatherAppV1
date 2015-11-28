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
import org.json.JSONArray;
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
        CityName = CityName.replaceAll(" ","");
        return "http://api.openweathermap.org/data/2.5/weather?q="+ CityName +"UK&units=metric&appid=80e6f18543ffb6265a664bad6d910eda" ;
    }
    /**
     This method build URL with Lat and Long
     @param Lat - City Latitude
     @param Long - City Longitude
     @return the value returned by the method - String with URL
     */
    public String CreateLatLongURL(String Lat,String Long)
    {

        return "http://api.openweathermap.org/data/2.5/weather?lat="+Lat+"&lon="+Long+"&units=metric&appid=80e6f18543ffb6265a664bad6d910eda";
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
        return "http://api.openweathermap.org/data/2.5/weather?q="+ CityName +"&" +unit+"=metric&appid=80e6f18543ffb6265a664bad6d910eda";
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
    public WeatherData ParseWeather(String feed,WeatherData weatherData)
    {
        final String TAG_COORD = "coord";
        final String TAG_LON = "lon";
        final String TAG_LAT = "lat";
        final String TAG_WEATHER = "weather";
        final String TAG_WEATHER_ID = "id";
        final String TAG_WEATHER_MAIN = "main";
        final String TAG_WEATHER_DESCRIPTION = "description";
        final String TAG_WEATHER_ICON = "icon";
        final String TAG_MAIN = "main";
        final String TAG_MAIN_TEMP = "temp";
        final String TAG_WIND = "wind";
        final String TAG_WIND_SPEED = "speed";
        final String TAG_CLOUDS = "clouds";
        final String TAG_CLOUDS_ALL = "all";
        final String TAG_RAIN = "rain";
        final String TAG_RAIN_3H = "3h";
        final String TAG_SNOW = "snow";
        final String TAG_SNOW_3H = "3h";
        final String TAG_SYS= "sys";
        final String TAG_SYS_SUNRISE= "sunrise";
        final String TAG_SYS_SUNSET= "sunset";
        final String TAG_ID= "id";
        final String TAG_NAME= "name";



        if (feed != null) { // if string empty skip this step
            try {
                JSONObject jsonObj = new JSONObject(feed); //Create JSON Object instance with weather data

                // Getting JSON Object Coord
               // JSONObject coord = jsonObj.getJSONObject(TAG_COORD);
              //  weatherData.setCoordLat(Double.parseDouble(coord.getString(TAG_LAT)));
              //  weatherData.setCoordLon(Double.parseDouble(coord.getString(TAG_LON)));
                // Getting JSON Array Weather
                JSONArray weatherArr = jsonObj.getJSONArray(TAG_WEATHER);// Get the array from the string
                JSONObject weatherObj = weatherArr.getJSONObject(0); //Get First Element (should exist only one)!No need for Loop
                weatherData.setWeatherID(weatherObj.getString(TAG_WEATHER_ID));
                weatherData.setWeatherMain(weatherObj.getString(TAG_WEATHER_MAIN));
                weatherData.setWeatherDesc(weatherObj.getString(TAG_WEATHER_DESCRIPTION));
                weatherData.setIcon(weatherObj.getString(TAG_WEATHER_ICON)+".png");//.png required to download the icon (not provided in the data )
                //next Object
                JSONObject main =jsonObj.getJSONObject(TAG_MAIN);
                weatherData.setMainTemp(Double.parseDouble(main.getString(TAG_MAIN_TEMP)));
                /**
                 * Some Objects not always exist which throw exception therefor each data need own try-catch statement
                **/
                //next
                try {
                    JSONObject wind = jsonObj.getJSONObject(TAG_WIND);
                    weatherData.setWindSpeed(wind.getString(TAG_WIND_SPEED));
                }
                catch (Exception e){}
                //next
                try {
                    JSONObject clouds = jsonObj.getJSONObject(TAG_CLOUDS);
                    weatherData.setClouds(clouds.getString(TAG_CLOUDS_ALL));
                }
                catch (Exception e){}
                //next
                try {
                JSONObject rain =jsonObj.getJSONObject(TAG_RAIN);
                weatherData.setRain3h(rain.getString(TAG_RAIN_3H));
                }
                catch (Exception e){}
                //next
                try {
                JSONObject snow =jsonObj.getJSONObject(TAG_SNOW);
                weatherData.setSnow3h(snow.getString(TAG_SNOW_3H));
                }
                catch (Exception e){}
                //next
                try {
                JSONObject sys =jsonObj.getJSONObject(TAG_SYS);
                weatherData.setSysSunrise(sys.getString(TAG_SYS_SUNRISE));
                weatherData.setSysSunset(sys.getString(TAG_SYS_SUNSET));
                }
                catch (Exception e){}
                try {
                //next, ID does not have a object
                weatherData.setID(jsonObj.getString(TAG_ID));
                }
                catch (Exception e){}
//              try {
//                    //next, name also does not have a object
//                    weatherData.setCity(jsonObj.getString(TAG_NAME));
//               }
//                catch (Exception e){}
                return weatherData;
            }
            catch (Exception e){return null;}
        }
        else
            return null;
    }
    public Bitmap GetImage(WeatherData weatherData) throws IOException {

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
