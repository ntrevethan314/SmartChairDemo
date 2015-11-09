package smartchairinc.smartchairdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class RoV_Sensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mRoV;

    float x;
    float y;
    float z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ro_v__sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mRoV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mRoV, 1000000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];


            TextView X_Value_TextView = (TextView) findViewById(R.id.P_VALUE);
            TextView Y_Value_TextView = (TextView) findViewById(R.id.R_VALUE);
            TextView Z_Value_TextView = (TextView) findViewById(R.id.Yw_VALUE);
            X_Value_TextView.setText(String.format("%.2f ", x * 180));
            Y_Value_TextView.setText(String.format("%.2f ", y * 180));
            Z_Value_TextView.setText(String.format("%.2f ", z * 180));
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

            if (Math.abs(x * 180) > 25) {
                TipEvent();
                Log.v("Dev Debug","" + x);
            }

            else if (Math.abs(y*180) > 25) {
                TipEvent();
                Log.v("Dev Debug", "" + y);
            }
            /**
             * else if (Math.abs(z*180) > 25) {
                TipEvent();
                Log.v("Dev Debug", "" + z);

            }*/
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mRoV, SensorManager.SENSOR_DELAY_NORMAL);
    }


    protected void TipEvent() {
        Intent myIntent = new Intent(RoV_Sensor.this, Location.class);
        RoV_Sensor.this.startActivity(myIntent);
    }
}
