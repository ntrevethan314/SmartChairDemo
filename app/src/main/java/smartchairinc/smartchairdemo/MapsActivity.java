package smartchairinc.smartchairdemo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Dev. Debug","Maps On Create Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        Log.v("Dev. Debug","OnMapReady 0");

        //Intent getLOC = getIntent();
        Log.v("Dev. Debug","OnMapReady 1");

        Bundle getLOC = getIntent().getExtras();
        Log.v("Dev. Debug","OnMapReady 2");

        double latitude = getLOC.getDouble("Latitude");
        double longitude = getLOC.getDouble("Longitude");
        Log.v("Dev. Debug","latitude"+latitude);
        Log.v("Dev. Debug","longitude"+longitude);


        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(latitude,longitude);
        //LatLng current = new LatLng(39.1288652,-84.5181448);
        mMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
