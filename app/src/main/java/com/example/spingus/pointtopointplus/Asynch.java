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
        urlString.append("https://maps.googleapis.com/maps/api/directions/json?");
        urlString.append("key=").append("AIzaSyDRTMLLebdTv8eaG4YQukxIOnZRjMjC7wU");
        urlString.append("&origin=").append(Nav.originLat + "," + Nav.originLon);
        urlString.append("&destination=").append(Nav.destinationLat + "," + Nav.destinationLon);
        for (int i = 0; i < Nav.numWaypoints; i++){
            urlString.append("&waypoints=").append(Nav.waypoints[i]);
        }
        urlString.append("&mode=").append(Nav.mode);

        Log.d("hi", urlString.toString());

        HttpURLConnection urlConnection = null;
        URL url = null;
        JSONObject object = null;

        LatLng[] latlng = new LatLng[50];

        try
        {
            url = new URL(urlString.toString());
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
            this.mException = e;
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