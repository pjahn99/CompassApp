package com.example.compassapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompassFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private SensorManager sensorManager ;
    private Sensor sensorAccelerometer ;
    private Sensor sensorMagnetometer ;

    private float[] rotationMatrix = new float[9] ;
    private float[] orientationAngles = new float[3] ;
    private float[] lastAcc = new float[3];
    private float[] lastMag = new float[3] ;

    private float[] smoothed=new float[3] ;
    //Compass View needed to draw the compass and show the data, that is calculated in this class
    private CompassView compassView ;

    //only needed for debugging purposes
    private TextView textView ;

    //own class, that is made for smoothing the sensor data
    private SignalSmoother signalSmoother ;

    public CompassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompassFragment newInstance(String param1, String param2) {
        CompassFragment fragment = new CompassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //arrange the sensors
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE) ;
        sensorAccelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ;
        sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) ;


        //creating a new Signal smoother instance, in order to make the data smoother
        //the higher the intensity, the slower but smoother the compass will move towards north
        signalSmoother=new SignalSmoother(15) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get reference to the Compass View and the textView(only for debugging purpose)
        View view=inflater.inflate(R.layout.fragment_compass,container,false) ;
        compassView=(CompassView) view.findViewById(R.id.compass_view) ;
        textView=(TextView) view.findViewById(R.id.textInfo);
        return view ;
    }

    public void onResume() {
        super.onResume();
        //activate the listener, when coming back to the app
        sensorManager.registerListener(this,sensorAccelerometer,SensorManager.SENSOR_DELAY_FASTEST) ;
        sensorManager.registerListener(this,sensorMagnetometer,SensorManager.SENSOR_DELAY_FASTEST) ;
    }

    @Override
    public void onPause() {
        super.onPause();
        //deactivate the listener when closing the app
        sensorManager.unregisterListener(this,sensorAccelerometer);
        sensorManager.unregisterListener(this,sensorMagnetometer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorManager.getRotationMatrix(rotationMatrix, null, lastAcc, lastMag);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        //the azimuth shows the direction towards north
        double azimuthInDegrees=(double) Math.toDegrees(orientationAngles[0]) ;
        //feed the signal smoother with the new value for the azimuth and get back the smoothed value for the azimuth
        /*
        double azimuthInDegrees_smoothed = signalSmoother.pushAndCalculate(azimuthInDegrees) ;
        compassView.setAzimuth((float)azimuthInDegrees_smoothed);
        textView.setText(Double.toString(azimuthInDegrees_smoothed)) ;
        */



        //store the new sensor readings (dependiing on which sensor caused the function) to lastAcc/lastMag
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            lastAcc = sensorEvent.values;
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            lastMag = sensorEvent.values ;
        }

        //get the new azimuth value to the compassView class, which then will draw line to the right direction
        compassView.setAzimuth((float)azimuthInDegrees);
        compassView.invalidate();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}