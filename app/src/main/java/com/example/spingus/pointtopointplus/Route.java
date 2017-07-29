package com.example.spingus.pointtopointplus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Route extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Asynch myTask = new Asynch(); // can add params for a constructor if needed
        myTask.execute();
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
        mMap = googleMap;


        while(Asynch.objy == null){
            Toast.makeText(Route.this, "Loading, Please wait", Toast.LENGTH_LONG);
        }
        JSONObject object = Asynch.objy;
        try {
            JSONArray routes = object.getJSONArray("routes");
            JSONObject legs = routes.getJSONObject(0);
            JSONArray legs1 = legs.getJSONArray("legs");
            PolylineOptions rectOptions = new PolylineOptions().width(5)
                    .color(Color.BLACK);
            rectOptions.add(new LatLng(Nav.originLat,Nav.originLon));
            Log.d("1", "here");
            for(int i = 0; i < legs1.length(); i++) {
                Log.d("2", "There");
                JSONObject steps = legs1.getJSONObject(i);
                JSONArray steps1 = steps.getJSONArray("steps");
                for (int j = 0; j < steps1.length(); j++) {
                    JSONObject endloc = steps1.getJSONObject(j);
                    JSONObject endloc1 = endloc.getJSONObject("end_location");
                    rectOptions.add(new LatLng(endloc1.getDouble("lat"), endloc1.getDouble("lng")));
                    Log.d("thissy", i+""+j);
                }
            }
            rectOptions.add(new LatLng(Nav.destinationLat,Nav.destinationLon));
            Polyline polyline = mMap.addPolyline(rectOptions);
            LatLng origin = new LatLng(Nav.originLat, Nav.originLon);
            mMap.addMarker(new MarkerOptions()
                    .position(origin)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 11));

            LatLng destination = new LatLng(Nav.destinationLat, Nav.destinationLon);
            mMap.addMarker(new MarkerOptions()
                    .position(destination)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));


            //for (int i = 0; i < routes.length(); i++) {
            //    JSONObject c = routes.getJSONObject(i);
            //}
        }catch(JSONException e){
            Log.d("Something Broke", e.toString());
            Double originLat = -35.289893;
            Double originLon = 149.127515;
            Double destinationLat = -35.2822;
            Double destinationLon = 149.1287;
            PolylineOptions rectOptions = new PolylineOptions().width(5)
                    .color(Color.BLACK);
            rectOptions.add(new LatLng(originLat,originLon));
            rectOptions.add(new LatLng(destinationLat,destinationLon));
            Polyline polyline = mMap.addPolyline(rectOptions);
            LatLng origin = new LatLng(originLat, originLon);
            mMap.addMarker(new MarkerOptions()
                    .position(origin)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));
            LatLng destination = new LatLng(destinationLat, destinationLon);
            mMap.addMarker(new MarkerOptions()
                    .position(destination)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 11));


        }
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
