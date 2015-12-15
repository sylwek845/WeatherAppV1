package prosoft.weatherv1;

import android.app.ProgressDialog;
import android.content.Intent;
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

    /**
     * Draw marker on the map
     * @param weatherData single weatherData object with data (cannot be null)
     * @return return complete marker
     */
    private MarkerOptions DrawMarker(WeatherData weatherData)
    {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // set bitmap conf
        Bitmap bmp = Bitmap.createBitmap(200, 100, conf); // create bitmap
        Canvas canvas1 = new Canvas(bmp); // Create new Canvas

        // defines the text
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
        Matrix matrixImage = new Matrix(); //use to scale and set position of image on the bit map
        matrixImage.setScale((float)1.5,(float)1.5);
        matrixImage.postTranslate(55,30);
        LatLng tmp = new LatLng(weatherData.getCoordLat(),weatherData.getCoordLon()); //create new lat long temporary var with location
        canvas1.drawBitmap(weatherData.getImage(), matrixImage, paintCity); //draw image
        canvas1.drawText(weatherData.getCity(), 5, 25, paintCity); // draw text (city name)
        canvas1.drawText(((int)weatherData.getMainTemp() + "°C"), 5, 65, paintTemp); //draw text (city temp)


        //add marker
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
            for (int i = 0; i < weatherDatas.length; i++) { //for every data
                try {
                    googleMap = maps[0]; // set map object to hold
                    Parser parser = new Parser(); // new parser instance
                    weatherDatas[i] = parser.ParseWeather(parser.GetSource(parser.CreateLatLongURL(String.valueOf(weatherDatas[i].getCoordLat()),String.valueOf(weatherDatas[i].getCoordLon()))),weatherDatas[i]); //get city Lat,get city Lon -> create URL -> Get Data -> Parse Data -> replace old with new object
                    Bitmap tmp =parser.GetImage(weatherDatas[i]); // get icon
                    if(tmp == null)
                    {
                        Log.e("Bitmap Empty","Downloaded Bitmap is empty");
                    }
                    else
                            weatherDatas[i].setImage(tmp);

                    publishProgress(i);//update progress
                    SystemClock.sleep(200); // sleep
                }
                catch (Exception e){}
            }
            return "";
        }

        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }
        protected void onPostExecute(String results) {
            //when Task Complete
            try {
                if (progress.isShowing()) {
                    // To dismiss the dialog
                    progress.dismiss();

                }
                AddMapAndMarkers(googleMap); //call method to add markers to map

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

        getLatLonFromGoogle(); // get positions
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //change type of map for satellite
        new LoadingTask().execute(googleMap); // get Data
    }

    /**
     * Gets Lat-Long Position from google servers based on city name
     */
    private void getLatLonFromGoogle()
    {
        weatherDatas = new WeatherData[Name.length]; //get name of the city using Name array
        for(int i = 0; i < Name.length;i++) { // for each name
            try {
                weatherDatas[i] = new WeatherData(); //create new weatherData object
                Geocoder geocoder = new Geocoder(this, Locale.getDefault()); //get local cities (e.g for glasgow it will get Glasgow lat long from local UK position, not for example US glasgow)
                List<Address> addresses; //list of addresses
                addresses = geocoder.getFromLocationName(Name[i], 1);
                if (addresses.size() > 0) { //only first one if any exist
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
        mMap.getUiSettings().setMapToolbarEnabled(false);//disable map tools
       // mMap.getUiSettings().setScrollGesturesEnabled(false); // disable moving of the map
        mMap.getUiSettings().setRotateGesturesEnabled(false);//disable gestures - rotation of map (using fingers)
        mMap.setOnMarkerClickListener(this); //set on marker click listener
        Markers = new Marker[weatherDatas.length]; // create new marker array

        for(int i =0;i<Markers.length;i++) //for each marker
        {
           // LatLng tmp = new LatLng(weatherDatas[i].getCoordLat(),weatherDatas[i].getCoordLon()); //create new lat long temporary var with location
            Markers[i] = mMap.addMarker(DrawMarker(weatherDatas[i])); //add marker to array
        }
        // Add  Scotland and move the camera
        LatLng scotland = new LatLng(57.27, -3.92); //move camera to this location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(scotland));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((scotland), 6.5f));
    }

    /**
     * On Click Marker Handler
     * @param marker objecy which was clicked
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {

        for(int i = 0; i < Markers.length;i++) // for each marker in markers array
        {
            if (marker.equals(Markers[i])) // if marker found
            {
                Toast.makeText(getApplicationContext(), weatherDatas[i].getCity(), Toast.LENGTH_SHORT).show(); //test
                DataExchanger.setWeatherDatas(weatherDatas); //set data in dataexchange class
                DataExchanger.setElement(i);//set which element was clicked
                Intent intent = new Intent(MainActivity.this,
                        ListActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }
}
