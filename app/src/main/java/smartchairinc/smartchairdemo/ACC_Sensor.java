package smartchairinc.smartchairdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ACC_Sensor extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private float x[] = {0, 0, System.nanoTime(), System.nanoTime()+5}; // [Current, New, Old_Time, New_Time]
    private float y[] = {0, 0, System.nanoTime(), System.nanoTime()+5}; // [Current, New, Old_Time, New_Time]
    private float z[] = {0, 0, System.nanoTime(), System.nanoTime()+5}; // [Current, New, Old_Time, New_Time]

    //private double time[] = {System.nanoTime(),System.nanoTime()+1}; // [Old, New]
    private double refresh = 9999999; // Min. difference between sensor updates
    //private double Gravity = 9.81;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc__sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x[1] = event.values[0];
            x[3] = System.nanoTime();
            y[1] = event.values[1];
            y[3] = System.nanoTime();
            z[1] = event.values[2];
            z[3] = System.nanoTime();

            X_Value_Modifier();
            Y_Value_Modifier();
            Z_Value_Modifier();

            //Log.v("********DUBUG**********",System.nanoTime()+" "+System.nanoTime()+1);
        }
    }

    // Change decides if value needs to be changed
    private void X_Value_Modifier() {
        TextView X_Value_TextView = (TextView) findViewById(R.id.X_VALUE);
        //Log.v("Dev_Debug", "" +Math.abs(x[2] - x[3]));
        if (Math.abs(x[2] - x[3]) > refresh) {
            x[0] = x[1];
            x[2] = x[3];
            X_Value_TextView.setText(String.format("%.2f ", x[0]));
            //   X_Value_TextView.setText(String.format("%.2f ", Math.abs(x[0] - Gravity) / Gravity * 100));

        }
    }

    private void Y_Value_Modifier() {
        TextView Y_Value_TextView = (TextView) findViewById(R.id.Y_VALUE);
        if (Math.abs(y[2] - y[3]) > refresh) {
            y[0] = y[1];
            y[2] = y[3];
            Y_Value_TextView.setText(String.format("%.2f", y[0]));
            //Y_Value_TextView.setText(String.format("%.2f", Math.abs(y[0]-Gravity)/Gravity*100));
        }
    }

    private void Z_Value_Modifier() {
        TextView Z_Value_TextView = (TextView) findViewById(R.id.Z_VALUE);
        if (Math.abs(z[2] - z[3]) > refresh) {
            z[0] = z[1];
            z[2] = z[3];
            Z_Value_TextView.setText(String.format("%.2f", z[0]));
            //Z_Value_TextView.setText(String.format("%.2f", Math.abs(z[1]-Gravity)/Gravity*100));
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
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
