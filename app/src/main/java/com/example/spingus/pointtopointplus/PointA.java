package com.example.spingus.pointtopointplus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PointA extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public Double lat = -34.30850783320357;
    public Double lon = 149.12460252642632;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.location.updateA(lat,lon);
                Intent intent = new Intent(PointA.this, Nav.class);
                startActivity(intent);
                //finish();
            }
        });
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

        LatLng a = new LatLng(MapsActivity.location.pointAlon, MapsActivity.location.pointAlat);
        mMap.addMarker(new MarkerOptions()
                .position(a)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));

        LatLng b = new LatLng(MapsActivity.location.pointBlon, MapsActivity.location.pointBlat);
        mMap.addMarker(new MarkerOptions()
                .position(b)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));

        LatLng sydney = new LatLng(-35.30850783320357, 149.12460252642632);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {


            @Override
            public void onMapClick(LatLng point) {

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("Choose Here");
                lat = point.latitude;
                lon = point.longitude;
                mMap.clear();
                mMap.addMarker(marker);
            }
        });
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
