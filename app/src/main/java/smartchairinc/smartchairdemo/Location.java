package smartchairinc.smartchairdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Location extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // LogCat tag
    private static final String TAG = Location.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private android.location.Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // NEW VARIABLES
    private double[] LOC = {0, 0}; // Latitude, Longitude


/**    // boolean flag to toggle periodic location updates
 private boolean mRequestingLocationUpdates = false;

 private LocationRequest mLocationRequest;

 // Location updates intervals in sec
 private static int UPDATE_INTERVAL = 10000; // 10 sec
 private static int FATEST_INTERVAL = 5000; // 5 sec
 private static int DISPLACEMENT = 10; // 10 meters
 */
    // UI elements
    //private TextView lblLocation;

    /**
     * private Button btnShowLocation, btnStartLocationUpdates;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }


    /**
     * Method to display the location on UI
     */
    public void displayLocation(View view) {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        TextView Location = (TextView) findViewById(R.id.lblLocation);

        if (mLastLocation != null) {
            LOC[0] = mLastLocation.getLatitude();
            LOC[1] = mLastLocation.getLongitude();
            Location.setText(String.format("Latitude: %.4f\nLongitude: %.4f", LOC[0], LOC[1]));

        } else {
            Location.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public void BeginMaps(View view) {
        displayLocation(view);
        Intent myIntent = new Intent(Location.this, MapsActivity.class);
        myIntent.putExtra("Latitude", LOC[0]);
        myIntent.putExtra("Longitude", LOC[1]);
        Location.this.startActivity(myIntent);

    }
}
