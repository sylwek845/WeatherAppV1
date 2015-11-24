package prosoft.weatherv1;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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

}
