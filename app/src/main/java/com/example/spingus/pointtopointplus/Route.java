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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 13));

            LatLng destination = new LatLng(Nav.destinationLat, Nav.destinationLon);
            mMap.addMarker(new MarkerOptions()
                    .position(destination)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pegman", 100, 100))));


            //for (int i = 0; i < routes.length(); i++) {
            //    JSONObject c = routes.getJSONObject(i);
            //}
        }catch(JSONException e) {
            Log.d("Something Broke", e.toString());
            Double originLat = -35.304849;
            Double originLon = 149.12579;
            Double destinationLat = -35.273449;
            Double destinationLon = 149.13283;
            Double waylat1 = -35.289464;
            Double waylon1 = 149.141405;
            Double waylat2 = -35.282389;
            Double waylon2 = 149.147854;
            PolylineOptions rectOptions = new PolylineOptions().width(5)
                    .color(Color.BLACK);
            rectOptions.add(new LatLng(originLat, originLon));
            rectOptions.add(new LatLng(waylat1, waylon1));
            rectOptions.add(new LatLng(waylat2, waylon2));
            rectOptions.add(new LatLng(destinationLat, destinationLon));
            Polyline polyline = mMap.addPolyline(rectOptions);
            LatLng origin = new LatLng(originLat, originLon);
            mMap.addMarker(new MarkerOptions()
                    .position(origin)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));
            LatLng destination = new LatLng(destinationLat, destinationLon);
            mMap.addMarker(new MarkerOptions()
                    .position(destination)
                    //.title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pointmarker", 100, 100))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 13));

        }
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
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
