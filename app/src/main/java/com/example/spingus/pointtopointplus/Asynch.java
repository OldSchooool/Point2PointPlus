package com.example.spingus.pointtopointplus;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spingus on 30/07/2017.
 */

public class Asynch extends AsyncTask<String, Void, JSONObject>
{
    Exception mException = null;
    public static JSONObject objy;
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.mException = null;
    }

    @Override
    protected JSONObject doInBackground(String... params)
    {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        urlString.append("key=").append("AIzaSyDRTMLLebdTv8eaG4YQukxIOnZRjMjC7wU");
        urlString.append("&rankby=distance");
        urlString.append("&location=").append(Nav.originLat + "," + Nav.originLon);

        Log.d("hi", urlString.toString());

        HttpURLConnection urlConnection1 = null;
        URL url1 = null;
        JSONObject object1 = null;
        try
        {
            url1 = new URL(urlString.toString());
            urlConnection1 = (HttpURLConnection) url1.openConnection();
            urlConnection1.setRequestMethod("GET");
            urlConnection1.setDoOutput(true);
            urlConnection1.setDoInput(true);
            urlConnection1.connect();
            InputStream inStream = null;
            inStream = urlConnection1.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null)
                response += temp;
            bReader.close();
            inStream.close();
            urlConnection1.disconnect();
            object1 = (JSONObject) new JSONTokener(response).nextValue();
            Log.d("hi", object1.toString());
        }
        catch (Exception e)
        {
            this.mException = e;
        }

        StringBuilder urlString2 = new StringBuilder();
        urlString2.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        urlString2.append("key=").append("AIzaSyDRTMLLebdTv8eaG4YQukxIOnZRjMjC7wU");
        urlString2.append("&rankby=distance");
        urlString2.append("&location=").append(Nav.destinationLat + "," + Nav.destinationLon);

        Log.d("hi", urlString2.toString());

        HttpURLConnection urlConnection2 = null;
        URL url2 = null;
        JSONObject object2 = null;
        try
        {
            url2 = new URL(urlString.toString());
            urlConnection2 = (HttpURLConnection) url2.openConnection();
            urlConnection2.setRequestMethod("GET");
            urlConnection2.setDoOutput(true);
            urlConnection2.setDoInput(true);
            urlConnection2.connect();
            InputStream inStream = null;
            inStream = urlConnection2.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null)
                response += temp;
            bReader.close();
            inStream.close();
            urlConnection2.disconnect();
            object2 = (JSONObject) new JSONTokener(response).nextValue();
            Log.d("hi", object2.toString());
        }
        catch (Exception e)
        {
            this.mException = e;
        }

        StringBuilder urlString3 = new StringBuilder();
        urlString3.append("https://maps.googleapis.com/maps/api/directions/json?");
        urlString3.append("key=").append("AIzaSyDRTMLLebdTv8eaG4YQukxIOnZRjMjC7wU");
        urlString3.append("&origin=").append(Nav.originLat + "," + Nav.originLon);
        urlString3.append("&destination=").append(Nav.destinationLat + "," + Nav.destinationLon);
        for (int i = 0; i < Nav.numWaypoints; i++){
            urlString3.append("&waypoints=").append(Nav.waypoints[i]);
        }
        urlString3.append("&mode=").append(Nav.mode);

        Log.d("hi", urlString3.toString());

        HttpURLConnection urlConnection = null;
        URL url = null;
        JSONObject object = null;

        LatLng[] latlng = new LatLng[50];

        try
        {
            url = new URL(urlString3.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inStream = null;
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null)
                response += temp;
            bReader.close();
            inStream.close();
            urlConnection.disconnect();
            object = (JSONObject) new JSONTokener(response).nextValue();

        }
        catch (Exception e)
        {
            Log.d("fubber", e.toString());
        }

        objy = object;
        return (object);
    }

    @Override
    protected void onPostExecute(JSONObject result)
    {
        super.onPostExecute(result);

    }

}