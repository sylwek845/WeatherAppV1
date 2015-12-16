package prosoft.weatherv1;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Sylwester Zalewski  on 2015.
 * Glasgow Caledonian University
 */

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(setting);


        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    public void onBackPressed()
    {
        //ParkingDataHold.setOtherActivityActive(false);
        super.onBackPressed();  // optional depending on your needs
    }

}
