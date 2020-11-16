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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrightnessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrightnessFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SensorManager sensorManager ;
    private Sensor sensorBrightness ;

    private BrightnessView brightnessView;

    public BrightnessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrightnessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrightnessFragment newInstance(String param1, String param2) {
        BrightnessFragment fragment = new BrightnessFragment();
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
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE) ;
        sensorBrightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_brightness, container, false);
        brightnessView=(BrightnessView) view.findViewById(R.id.brightness_view) ;
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorBrightness,SensorManager.SENSOR_DELAY_NORMAL) ;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,sensorBrightness);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        brightnessView.setBrightness(sensorEvent.values[0]) ;
        brightnessView.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}