package smartchairinc.smartchairdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class SensorLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button findsensorbttn = (Button) findViewById(R.id.DetecSensorsbttn);
        findsensorbttn.setVisibility(View.GONE);
    }

    public void BeginACC(View view){
        Intent myIntent = new Intent(SensorLauncher.this,ACC_Sensor.class);
        SensorLauncher.this.startActivity(myIntent);

    }

    public void BeginRoV(View view){
        Intent myIntent = new Intent(SensorLauncher.this,RoV_Sensor.class);
        SensorLauncher.this.startActivity(myIntent);

    }
    private SensorManager mSensorManager;

    public void DetectSensors(View view){
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.v("DEV_DEBUG", "DetectSensors Called");
    }
}
