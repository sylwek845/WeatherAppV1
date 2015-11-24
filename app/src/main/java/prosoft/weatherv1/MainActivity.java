package prosoft.weatherv1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        for(int i =0;i<3;i++)
        {
            WeatherData[] weatherDatas = testCities();
            LatLng tmp = new LatLng(weatherDatas[i].getCoordLat(),weatherDatas[i].getCoordLon());
            mMap.addMarker(new MarkerOptions().position(tmp).title("Marker in "+weatherDatas[i].getCity() ));

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
        for(int i = 0; i < 3;i++)
        {
            weatherData[i] = new WeatherData();
            weatherData[i].setCity(Name[i]);
            weatherData[i].setCoordLat(Lat[i]);
            weatherData[i].setCoordLon(Lon[i]);
        }
        return weatherData;
    }
}
