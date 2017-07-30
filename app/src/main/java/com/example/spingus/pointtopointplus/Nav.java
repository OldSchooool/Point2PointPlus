package com.example.spingus.pointtopointplus;
//https://maps.googleapis.com/maps/api/directions/json?origin=41.43206,-81.38992&destination=origin=42.43206,-82.38992&waypoints=41.43206,-82.38992&mode=walking&key=AIzaSyDRTMLLebdTv8eaG4YQukxIOnZRjMjC7wU
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/*
    -35.304849,149.12579
    -35.289464,149.141405
    -35.282389,149.147854
    -35.273449,149.132838
*/

import static com.example.spingus.pointtopointplus.R.id.map;

public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    public static boolean bike,art,bench,walk,arts,police,drinking,dog,fitness,graffiti,hospital,library,pedestrian,bbq,furniture,toilets,skate,park,traffic = false;

    public static Double originLat = -35.304849;
    public static Double originLon = 149.12579;
    public static Double destinationLat = -35.273449;
    public static Double destinationLon = 149.13283;
    public static String mode = "walking";
    public static String[] waypoints = new String[10];
    public static int numWaypoints = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        waypoints[0] = "-35.289464,149.141405";
        waypoints[1] = "-35.282389,149.147854";
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Nav.this, PointA.class);
                startActivity(i);
            }
        });


        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Nav.this, PointB.class);
                startActivity(i);
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Nav.this, Modifiers.class);
                startActivity(i);
            }
        });

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Nav.this, Route.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(Nav.this, Instructions.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(Nav.this, Credits.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
        StringBuilder text = new StringBuilder();

        if(bike) {
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
        if (park){
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
        if (bbq){
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
        if (toilets){
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
        //public furniture
        if (furniture){
            //Park Data
            InputStream inputStream5 = getResources().openRawResource(R.raw.public_furniture_in_the_act);
            CSVFile csvFile5 = new CSVFile(inputStream5);
            String[] scoreList5 = csvFile5.read(4040);
            for (int i = 1; i < 4039; i++) {
                String[] s5 = scoreList5[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s5[6]), Double.parseDouble(s5[7]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Furniture")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
        //public art
        if (art){
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


    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public void car(View view){
        mode = "driving";
    }
    public void ride(View view){
        mode = "cycling";
    }
    public void walk(View view){
        mode = "walking";
    }
}
