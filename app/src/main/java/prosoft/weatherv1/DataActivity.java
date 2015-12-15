package prosoft.weatherv1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sylwester Zalewski on 15/12/2015.
 */
public class DataActivity extends Activity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_data);

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

        text_city.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getCity());
        text_desc.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getWeatherDesc());
        text_main_temp.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getMainTempString());
        text_windSpeed.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getWindSpeed());
        text_clouds.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getClouds());
        text_rain.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getRain3h());
        text_snow.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSnow3h());
        text_sunrise.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSysSunrise());
        text_sunset.setText(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getSysSunset());
        image_icon.setImageBitmap(DataExchanger.getWeatherDatas()[DataExchanger.getElement()].getImage());
    }


}
