package com.example.student.lab9_fri;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, LocationListener{

    int n=0;
    int numofLocation=0;
    LatLng lastlocation = null;

    LocationManager lMger;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mMap.setOnMapLongClickListener(this);  //setup longclick
        lMger = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lMger.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);  //provider, mintime to update, min destance
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();     //check if the map already create
    }


    @Override
    public void onMapLongClick(LatLng latLng) {    //ctrl i - show method that not yet implement
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker " + ++n));  //sent position of marker to be the received long click input
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {    //if not create the map yet, then setupmap
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    private void setUpMap() {     //call when first time the map create
        LatLng BKK = new LatLng(13.736717, 100.523186);   //can set as variable
        LatLng HK = new LatLng(22.25, 114.1667);
        LatLng VT = new LatLng(20.993776, 105.811417);

       // mMap.addMarker(new MarkerOptions().position(BKK).title("Bangkok"));    //set default marker
       // mMap.addMarker(new MarkerOptions().position(HK).title("Hong Kong"));
       // mMap.addMarker(new MarkerOptions().position(VT).title("Hanoi"));

        //Move map center point of BKK
        CameraUpdate center_point = CameraUpdateFactory.newLatLng(VT);  //set the center of the map
        mMap.moveCamera(center_point);    ///update

        //change map zoom level
        CameraUpdate zoom_level = CameraUpdateFactory.zoomTo(5);  //level 1-10
        mMap.animateCamera(zoom_level);     ///update zoom

        //Draw polyline on the map
        //First Way
        //mMap.addPolyline(new PolylineOptions().width(3).add(BKK).add(VT).add(HK));

        //Second Way
      //  mMap.addPolyline(new PolylineOptions().width(3).add(BKK).add(VT));
      //  mMap.addPolyline(new PolylineOptions().width(3).add(VT).add(HK));
    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng LL = new LatLng(location.getLatitude(), location.getLongitude());  //get Latitude etc. from the given location

        mMap.addMarker(new MarkerOptions().position(LL).title("Location Change " + ++numofLocation));
        //Move map center point of BKK
        CameraUpdate center_point = CameraUpdateFactory.newLatLng(LL);  //set the center of the map
        mMap.moveCamera(center_point);
        CameraUpdate zoom_level = CameraUpdateFactory.zoomTo(7);  //level 1-10
        mMap.animateCamera(zoom_level);
        if(lastlocation == null){
            lastlocation = LL;
        }
        mMap.addPolyline(new PolylineOptions().width(3).add(lastlocation).add(LL));
        lastlocation = LL;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
