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

import static com.example.spingus.pointtopointplus.R.id.map;

public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    public boolean bike,art,arts,police,drinking,dog,fitness,graffiti,hospital,library,pedestrian,bbq,furniture,toilets,skate,park,traffic = false;

    public static Double originLat = 41.43206;
    public static Double originLon = -81.38992;
    public static Double destinationLat = 42.43206;
    public static Double destinationLon = -82.38992;
    public static String mode = "walking";
    public static String[] waypoints = new String[10];
    public static int numWaypoints = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        waypoints[0] = "41.43206,-82.38992";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = new Intent(Nav.this, Route.class);
        startActivity(i);

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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));

        LatLng b = new LatLng(MapsActivity.location.pointBlon, MapsActivity.location.pointBlat);
        mMap.addMarker(new MarkerOptions()
                .position(b)
                //.title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));

        LatLng sydney = new LatLng(-35.30850783320357, 149.12460252642632);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        StringBuilder text = new StringBuilder();

        bike = true;
        park = true;

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
            for (int i = 1; i < 65; i++) {
                String[] s2 = scoreList2[i].split(",");
                LatLng pos = new LatLng(Double.parseDouble(s2[7]), Double.parseDouble(s2[8]));
                mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Playground")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("locationmarker", 40, 40))));
            }
        }
    }


    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}