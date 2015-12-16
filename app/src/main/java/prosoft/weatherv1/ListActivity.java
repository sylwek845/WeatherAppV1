package prosoft.weatherv1;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylwester Zalewski on 15/12/2015.
 */
public class ListActivity extends Activity {

    private List<WeatherData> weatherDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Popthelist();
        populateListView();

    }
    private void populateListView() {
        ArrayAdapter<WeatherData> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.ListView);

        list.setAdapter(adapter);
    }
    private void Popthelist()
    {
        weatherDatas = new ArrayList<WeatherData>();
        for(int i = 0;i< DataExchanger.getWeatherDatas().length;i++)
        {
            weatherDatas.add(DataExchanger.getWeatherDatas()[i]);
        }
    }





    private class MyListAdapter extends ArrayAdapter<WeatherData> {
        public MyListAdapter() {
            super(ListActivity.this, R.layout.item_view, weatherDatas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View Itemview = convertView;
            if (Itemview == null) {
                Itemview = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            WeatherData data = weatherDatas.get(position);

            ImageView img = (ImageView) Itemview.findViewById(R.id.item_image);
            img.setImageDrawable(new BitmapDrawable(getResources(),data.getImage()));
            TextView name = (TextView) Itemview.findViewById(R.id.item_txtName);
            name.setText(data.getCity());
            TextView main = (TextView) Itemview.findViewById(R.id.item_Main);
            main.setText(String.valueOf(data.getWeatherMain()));
            TextView temp = (TextView) Itemview.findViewById(R.id.item_Temp);
            if(DataExchanger.isCelsius())
                temp.setText(String.valueOf(data.getMainTempString()));
            else {
                double dtemp = data.getMainTemp();
                dtemp = dtemp * 9 / 5 + 32;
                temp.setText(String.valueOf(Math.round(dtemp)) + "°F");
            }
            return Itemview;
        }

    }
}
