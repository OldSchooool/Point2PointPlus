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

import java.io.InputStream;

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
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));

        LatLng b = new LatLng(MapsActivity.location.pointBlon, MapsActivity.location.pointBlat);
        mMap.addMarker(new MarkerOptions()
                .position(b)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));

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

        if(Nav.bike) {
            //Bike crash data
            InputStream inputStream = getResources().openRawResource(R.raw.cyclist_crashes);
            CSVFile csvFile = new CSVFile(inputStream);
            String[] scoreList = csvFile.read(1110);
            for (int i = 1; i < 1110; i++) {
                String[] s = scoreList[i].split(",");

                LatLng pos = new LatLng(Double.parseDouble(s[8]), Double.parseDouble(s[9]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Bike Crash")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));

            }
        }
        if (Nav.park){
            //Park Data
            InputStream inputStream2 = getResources().openRawResource(R.raw.town_and_district_playgrounds);
            CSVFile csvFile2 = new CSVFile(inputStream2);
            String[] scoreList2 = csvFile2.read(70);
            for (int i = 1; i < 69; i++) {
                String[] s2 = scoreList2[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s2[7]), Double.parseDouble(s2[8]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Playground")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        //public art
        if (Nav.bbq){
            //Park Data
            InputStream inputStream3 = getResources().openRawResource(R.raw.public_barbeques_in_the_act);
            CSVFile csvFile3 = new CSVFile(inputStream3);
            String[] scoreList3 = csvFile3.read(328);
            for (int i = 1; i < 327; i++) {
                String[] s3 = scoreList3[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s3[6]), Double.parseDouble(s3[7]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Barbeque Location")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        //public art
        if (Nav.toilets){
            //Park Data
            InputStream inputStream4 = getResources().openRawResource(R.raw.public_toilets_in_the_act);
            CSVFile csvFile4 = new CSVFile(inputStream4);
            String[] scoreList4 = csvFile4.read(134);
            for (int i = 1; i < 133; i++) {
                Log.d("hi" , i + "");
                String[] s4 = scoreList4[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s4[6]), Double.parseDouble(s4[7]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Toilets")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        //public art
        if (Nav.furniture){
            //Park Data
            InputStream inputStream5 = getResources().openRawResource(R.raw.public_furniture_in_the_act);
            CSVFile csvFile5 = new CSVFile(inputStream5);
            String[] scoreList5 = csvFile5.read(4040);
            for (int i = 1; i < 4039; i++) {
                String[] s5 = scoreList5[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s5[6]), Double.parseDouble(s5[7]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Toilets")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        if (Nav.art){
            //Park Data
            InputStream inputStream6 = getResources().openRawResource(R.raw.act_public_art_list);
            CSVFile csvFile6 = new CSVFile(inputStream6);
            String[] scoreList6 = csvFile6.read(133);
            for (int i = 1; i < 132; i++) {
                String[] s6 = scoreList6[i].split("-");
                String[] thi = s6[1].split(",");
                String[] lon = thi[1].split(" ");
                String lony = lon[1].substring(0, lon[1].length() - 2);
                LatLng pos = new LatLng(Double.parseDouble("-" + thi[0]), Double.parseDouble(lony));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Public Art")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        LatLng origin = new LatLng(Nav.originLat, Nav.originLon);
        mMap.addMarker(new MarkerOptions()
                .position(origin)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));

        LatLng destination = new LatLng(Nav.destinationLat, Nav.destinationLon);
        mMap.addMarker(new MarkerOptions()
                .position(destination)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));

    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
