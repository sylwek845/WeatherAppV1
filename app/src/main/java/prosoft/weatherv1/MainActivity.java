package prosoft.weatherv1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker[] Markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private MarkerOptions DrawMarker(WeatherData weatherData)
    {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
        Canvas canvas1 = new Canvas(bmp);

    // paint defines the text color,
    // stroke width, size
        Paint color = new Paint();
        Paint colorwhite = new Paint();
        colorwhite.setColor(Color.WHITE);
        color.setTextSize(20);
        color.setColor(Color.BLACK);
        Paint temperatureColor = new Paint();
        temperatureColor.setTextSize(40);
        temperatureColor.setColor(Color.BLACK);
        Matrix matrixImage = new Matrix();
        matrixImage.setScale((float)0.5,(float)0.5);
        matrixImage.postTranslate(50,50);
        LatLng tmp = new LatLng(weatherData.getCoordLat(),weatherData.getCoordLon());
    //modify canvas
        canvas1.drawRect(0, 0, 100, 100, color);
        canvas1.drawRect(3, 3, 97, 97, colorwhite);
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                weatherData.getImage()),matrixImage, color);
        canvas1.drawText(weatherData.getCity(), 5, 25, color);
        canvas1.drawText((weatherData.getMainTemp() + "C"), 5, 75, color);

    //add marker to Map
        MarkerOptions marker = new MarkerOptions().position(tmp)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1);





        return marker;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        Markers = new Marker[3];

        for(int i =0;i<3;i++)
        {
            WeatherData[] weatherDatas = testCities();
            LatLng tmp = new LatLng(weatherDatas[i].getCoordLat(),weatherDatas[i].getCoordLon());
           // mMap.addMarker(new MarkerOptions().position(tmp).title("Marker in "+weatherDatas[i].getCity() ));
            Markers[i] = mMap.addMarker(DrawMarker(weatherDatas[i]));
          //  mMap.addMarker(DrawMarker(weatherDatas[i]));

        }



        // Add a marker in Sydney and move the camera
        LatLng scotland = new LatLng(57.27, -3.92);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(scotland));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((scotland), 6.5f));
    }

    /**
     * This is a test method which created city objects with weather
     * @return Return x number of cities as WeatherData array
     */
    private WeatherData[] testCities()
    {
        String[] Name = {"Glasgow", "Aberdeen", "Edinburgh"};
        double[] Lat = {55.87, 57.14, 55.95};
        double[] Lon = {-4.26, -2.1,-3.2};
        WeatherData[] weatherData = new WeatherData[3];
        double[] maintemp = {4.26, -2.1, 9};
        for(int i = 0; i < 3;i++)
        {
            weatherData[i] = new WeatherData();
            weatherData[i].setCity(Name[i]);
            weatherData[i].setCoordLat(Lat[i]);
            weatherData[i].setCoordLon(Lon[i]);
            weatherData[i].setImage(R.drawable.a10d);
            weatherData[i].setMainTemp(maintemp[i]);
        }
        return weatherData;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(Markers[0]))
        {
            Toast.makeText(getApplicationContext(), "Glasgow", Toast.LENGTH_SHORT).show();
        }
        else  if (marker.equals(Markers[1]))
        {
            Toast.makeText(getApplicationContext(), "Aberdeen", Toast.LENGTH_SHORT).show();
        }
        else  if (marker.equals(Markers[2]))
        {
            Toast.makeText(getApplicationContext(), "Edinburgh", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
