package prosoft.weatherv1;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker[] Markers;
    private WeatherData[] weatherDatas;
    private String[] Name = {"Glasgow", "Aberdeen", "Edinburgh","Perth","Elgin","Fort William","Oban","Wick","Tongue"};
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
        Bitmap bmp = Bitmap.createBitmap(200, 100, conf);
        Canvas canvas1 = new Canvas(bmp);

    // paint defines the text paintCity,
    // stroke width, size
        Paint paintCity = new Paint();
        Paint paintTemp = new Paint();
        paintTemp.setTextSize(30);
        paintTemp.setStyle(Paint.Style.FILL);
        paintTemp.setColor(Color.WHITE);
        paintCity.setTextSize(30);
        paintCity.setStyle(Paint.Style.FILL);
        paintCity.setStrokeJoin(Paint.Join.BEVEL);
        paintCity.setStrokeMiter(50);
        paintCity.setColor(Color.WHITE);
        Matrix matrixImage = new Matrix();
        matrixImage.setScale((float)1.5,(float)1.5);
        matrixImage.postTranslate(55,30);
        LatLng tmp = new LatLng(weatherData.getCoordLat(),weatherData.getCoordLon());
    //modify canvas

        //canvas1.drawRect(0, 0, 100, 100, paintCity);
      //  canvas1.drawRect(3, 3, 97, 97, colorwhite);
        Bitmap bitmap =weatherData.getImage();
        canvas1.drawBitmap(weatherData.getImage(), matrixImage, paintCity);
        canvas1.drawText(weatherData.getCity(), 5, 25, paintCity);
        canvas1.drawText(((int)weatherData.getMainTemp() + "°C"), 5, 65, paintTemp);


    //add marker to Map
        MarkerOptions marker = new MarkerOptions().position(tmp)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1);





        return marker;
    }

    private class LoadingTask extends AsyncTask<GoogleMap, Integer, String> {
        ProgressDialog progress = new ProgressDialog(MainActivity.this);
        GoogleMap googleMap;
        protected String doInBackground(GoogleMap... maps) {
            for (int i = 0; i < weatherDatas.length; i++) {
                try {
                    googleMap = maps[0];
                    Parser parser = new Parser();
                    weatherDatas[i] = parser.ParseWeather(parser.GetSource(parser.CreateLatLongURL(String.valueOf(weatherDatas[i].getCoordLat()),String.valueOf(weatherDatas[i].getCoordLon()))),weatherDatas[i]); //get city Lat,get city Lon -> create URL -> Get Data -> Parse Data -> replace old with new object
                    Bitmap tmp =parser.GetImage(weatherDatas[i]); // get icon
                    if(tmp == null)
                    {
                        Log.e("Bitmap Empty","Downloaded Bitmap is empty");
                    }
                    else
                            weatherDatas[i].setImage(tmp);

                publishProgress(i);
                    SystemClock.sleep(200);
                }
                catch (Exception e){}
            }
            return "";
        }
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }
        protected void onPostExecute(String results) {
            try {
                if (progress.isShowing()) {
                    // To dismiss the dialog
                    progress.dismiss();

                }
                AddMapAndMarkers(googleMap);
                //when Task Complete
            } catch (Exception e) {
            }
        }

        @Override
        protected void onPreExecute() {
            //Before Task begin
            progress.setTitle("Loading");
            progress.setMessage("Please Wait..");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMax(weatherDatas.length);
            progress.show();
        }

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

        getLatLonFromGoogle();
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        new LoadingTask().execute(googleMap);



    }
    private void getLatLonFromGoogle()
    {
        weatherDatas = new WeatherData[Name.length];
        for(int i = 0; i < Name.length;i++) {
            try {
                weatherDatas[i] = new WeatherData();
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(Name[i], 1);
                if (addresses.size() > 0) {
                    weatherDatas[i].setCoordLat(addresses.get(0).getLatitude());
                    weatherDatas[i].setCoordLon(addresses.get(0).getLongitude());
                    weatherDatas[i].setCity(Name[i]);
                }
            }
            catch (Exception e) {}
        }
    }
    private void AddMapAndMarkers(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
       // mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnMarkerClickListener(this);
        Markers = new Marker[weatherDatas.length];

        for(int i =0;i<Markers.length;i++)
        {

            LatLng tmp = new LatLng(weatherDatas[i].getCoordLat(),weatherDatas[i].getCoordLon());
            // mMap.addMarker(new MarkerOptions().position(tmp).title("Marker in "+weatherDatas[i].getCity() ));
            Markers[i] = mMap.addMarker(DrawMarker(weatherDatas[i]));
            //  mMap.addMarker(DrawMarker(weatherDatas[i]));

        }



        // Add  Scotland and move the camera
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
        String[] Name = {"Glasgow", "Aberdeen", "Edinburgh","Dundee","Perth","Elgin","Fort William","Oban"};
       // double[] Lat = {55.87, 57.14, 55.95};
     //   double[] Lon = {-4.26, -2.1,-3.2};
        WeatherData[] weatherData = new WeatherData[8];
     //   double[] maintemp = {4.26, -2.1, 9};
        for(int i = 0; i < Name.length;i++)
        {
            weatherData[i] = new WeatherData();
            weatherData[i].setCity(Name[i]);
     //       weatherData[i].setCoordLat(Lat[i]);
     //       weatherData[i].setCoordLon(Lon[i]);
            //weatherData[i].setImage(BitmapFactory.decodeResource(getResources(), R.drawable.a10d));

      //      weatherData[i].setMainTemp(maintemp[i]);
        }
     //   weatherData[0].setIcon("10d.png");
     //   weatherData[1].setIcon("11d.png");
      //  weatherData[2].setIcon("50n.png");
        return weatherData;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        for(int i = 0; i < Markers.length;i++)
        {
            if (marker.equals(Markers[i]))
            {
                Toast.makeText(getApplicationContext(), weatherDatas[i].getCity(), Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
