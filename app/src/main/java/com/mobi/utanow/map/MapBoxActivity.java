package com.mobi.utanow.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;
import com.mobi.utanow.R;

public class MapBoxActivity extends AppCompatActivity {

    private MapView mapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_box);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        double xco = 0;
        double yco = 0;

        /** Create a mapView and give it some properties */
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.DARK);

        if(extras!=null) {
            xco = extras.getDouble("xco", 0);//get x cordinance4
            yco = extras.getDouble("yco", 0);
        }
        if((xco != 0) && (yco != 0)) { //can't compare to NULL? What hapens if the key for the bundle is invalid? does it return 0?
            mapView.setLatLng(new LatLng(xco,yco));
        }
        else {
            mapView.setLatLng(new LatLng(32.729, -97.115));
        }

        mapView.setZoom(17.5);
        mapView.addMarker(new MarkerOptions().title("Hello World!")
                        .snippet("Party Here!!")
                        .position(new LatLng(32.729,-97.115)));
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
