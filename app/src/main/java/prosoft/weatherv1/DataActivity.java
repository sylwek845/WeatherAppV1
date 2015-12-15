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
    private TextView text_;
    private ImageView image_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_data);
    }
}
