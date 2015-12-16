package prosoft.weatherv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sylwester Zalewski on 15/12/2015.
 */
public class DataActivity extends AppCompatActivity {
    /**
     * private double coordLon;
     private double coordLat;
     private String weatherID;
     private String weatherMain;
     private String weatherDesc;
     private double mainTemp;
     private String windSpeed;
     private String clouds;
     private String rain3h;
     private String snow3h;
     private String sysSunrise;
     private String sysSunset;
     private String ID;
     private String City;
     private String Icon;
     private Bitmap image;
     */

    private TextView text_city;
    private TextView text_main;
    private TextView text_desc;
    private TextView text_main_temp;
    private TextView text_windSpeed;
    private TextView text_clouds;
    private TextView text_rain;
    private TextView text_snow;
    private TextView text_sunrise;
    private TextView text_sunset;
    private ImageView image_icon;
    private boolean celsius = true;
    private boolean mph =true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getCity());
        text_city =  (TextView) findViewById(R.id.city_name);
        text_desc = (TextView) findViewById(R.id.city_desc);
        text_main_temp = (TextView) findViewById(R.id.city_temp);
        text_windSpeed  = (TextView) findViewById(R.id.city_wind);
        text_clouds = (TextView) findViewById(R.id.city_clouds);
        text_rain = (TextView) findViewById(R.id.city_rain);
        text_snow  = (TextView) findViewById(R.id.city_snow);
        text_sunrise  = (TextView) findViewById(R.id.city_sunrise);
        text_sunset = (TextView) findViewById(R.id.city_sunset);
        image_icon  = (ImageView) findViewById(R.id.city_icon);


        addValues();

    }

    public void addValues()
    {
        if(celsius)
            text_main_temp.setText("Temperature: " +DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getMainTempString()+ "°C");
        else {
            double temp = DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getMainTemp();
            temp = temp * 9/5 + 32;
            text_main_temp.setText("Temperature: " + String.valueOf(temp) + "°F");
        }
        text_city.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getCity());
        text_desc.setText("Weather Description: " +DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getWeatherDesc());
        if(mph)
        text_windSpeed.setText("Wind: " + DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getWindSpeed()+ " mph");
        else
        {
            double wind = Double.parseDouble(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getWindSpeed());
            wind = wind * 1.609344;
            text_windSpeed.setText("Wind: " + String.valueOf(wind) + " kph");
        }
        text_clouds.setText("Clouds: " +DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getClouds()+ "%");
        try {
            if (DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getRain3h().isEmpty())
                text_rain.setText("Rain: N/A");
            else
                text_rain.setText("Rain: " + DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getRain3h()+ " mm");
        }
        catch (Exception e){
            text_rain.setText("Rain: N/A");
        }
        try {
            if (DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSnow3h().isEmpty())
                text_snow.setText("Snow: N/A");

            else
                text_snow.setText("Snow: " + DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSnow3h()+" mm");
        }
        catch (Exception e){
            text_snow.setText("Snow: N/A");
        }
        long unixTimestamp =Long.parseLong(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSysSunrise());
        long javaTimestamp = unixTimestamp * 1000L;
        Date date = new Date(javaTimestamp);
        String sunrise = new SimpleDateFormat("hh:mm").format(date);
        unixTimestamp =  Long.parseLong(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSysSunset());
        javaTimestamp = unixTimestamp * 1000L;
        date = new Date(javaTimestamp);
        String sunset = new SimpleDateFormat("hh:mm").format(date);
        text_sunrise.setText("Sunrise: " +sunrise);
        text_sunset.setText("Sunset: "+sunset);
        image_icon.setImageBitmap(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getImage());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(DataActivity.this,
                    About.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_list) {
            Intent intent = new Intent(DataActivity.this,
                    ListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
