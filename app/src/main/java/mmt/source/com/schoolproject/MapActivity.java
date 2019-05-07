package mmt.source.com.schoolproject;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback,GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;
    private MarkerOptions place1, place2, place3;
    Button getDirection;
    private Polyline currentPolyline;
    LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("shiva get direction");
/*
                // Destination of route
                String str_origin = "origin=" + 17.9149584 + "," + 77.4695904;
                String str_dest = "destination=" + 12.97168 + "," + 77.4695904;
                String sensor = "sensor=false";

                // Building the parameters to the web service
                String parameters = str_origin + "&" + str_dest + "&" + sensor;

                // String parameters = str_origin + "&" + str_dest + "&";
                // Output format
                String output = "json";

                String url = "https://maps.googleapis.com/maps/api/distancematrix/" + output +"?"
                        + parameters + "&mode=walking&key=" +"Android_Key";
                */
                new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

            }
        });

        //27.658143,85.3199503
        //27.667491,85.3208583
        place1 = new MarkerOptions().position(new LatLng(17.9149584, 77.4695904)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(12.97168, 77.4695904)).title("Location 2");
        place3 = new MarkerOptions().position(new LatLng(13.0827, 80.2707)).title("Location 3");
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
        //runThread();
    }

    private void runThread(){
        runOnUiThread (new Thread(new Runnable() {
            public void run() {
                //while (true)
                {
                    try {
                        System.out.println("shiva thread started");
                        Thread.sleep(100000);
                        System.out.println("shiva thread triggered to set new location");
                        mMap.addMarker(place3);
                        mMap.addMarker(place2);
                        LatLng india = new LatLng(17.9149584, 77.559543);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 6.1f));
                        Thread.sleep(100000);
                    }catch (Exception e ) {
                        System.out.println("fatal shiva thread triggered to set new location");
                    }
                }
            }
        }));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("shiva on map ready");
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        LatLng india = new LatLng(17.9149584,77.559543);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india,6.1f));
/*
        try {
            Thread.sleep(10000);
        }catch (Exception e) {

        }
        System.out.println("shiva on map ready set again ");
        mMap.addMarker(place3);
        mMap.addMarker(place2);
        india = new LatLng(17.9149584, 77.559543);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 6.1f));
*/
    }



    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
    protected GoogleMap getMap() {
        return mMap;
    }
/*
    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){
        final String parsedDistance;
        final String response;
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance=distance.getString("text");

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance;
    }*/
}

