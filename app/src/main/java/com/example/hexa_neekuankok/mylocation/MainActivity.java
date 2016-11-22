package com.example.hexa_neekuankok.mylocation;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.vision.text.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
//    private double longitude = 0.0;
//    private double latitude = 0.0;
    @Bind(R.id.latitude) TextView tvLatitude;
    @Bind(R.id.longititude) TextView tvLongitude;
    @Bind(R.id.auto_latitude) TextView tv_autoLatitude;
    @Bind(R.id.auto_longititude) TextView tv_autoLongitude;
    @Bind(R.id.availability) TextView tv_availability;
    @Bind(R.id.updateLocation) Button updateButton;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 888;
    private static final int REQUEST_CHECK_SETTINGS = 555;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE_MSG = "latitude_msg";
    private static final String LONGITUDE_MSG = "longitude_msg";
    private static final String LOCATION_AVAILABILITY = "availability";
    private LocationRequest mLocationRequest;
    private LocationListener locationListener;

    //@Inject
    //MyLocation myLocation;
    MyLocation myLocation;

    LocationInstanceState myLocationInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ((App)getApplication()).getAppComponent().passIn(this);
        myLocationInstanceState = new LocationInstanceState();
        //myLocation = new MyLocation1();
        ((App)getApplication()).getAppComponent().passIn(myLocationInstanceState);
        //myLocationInstanceState.setMyLocation(myLocation);
        myLocation = myLocationInstanceState.getMyLocation();

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
        }

        locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location){
                if(location != null){
                    double auto_longitude = location.getLongitude();
                    double auto_latitude = location.getLatitude();
                    tv_autoLongitude.setText(Double.toString(auto_longitude));
                    tv_autoLatitude.setText(Double.toString(auto_latitude));
                }
            }
        };

        //commented out to use butterknife
//        tvLatitude = (TextView)findViewById(R.id.latitude);
//        tvLongitude = (TextView)findViewById(R.id.longititude);
//        tv_autoLatitude = (TextView)findViewById(R.id.auto_latitude);
//        tv_autoLongitude = (TextView)findViewById(R.id.auto_longititude);
//        tv_availability = (TextView)findViewById(R.id.availability);
//        updateButton = (Button)findViewById(R.id.updateLocation);

        ButterKnife.bind(this);
        //Commented out to test using dagger2 to persist instance state
//        if(savedInstanceState != null){
//            latitude = savedInstanceState.getDouble(LATITUDE);
//            longitude = savedInstanceState.getDouble(LONGITUDE);

//            tvLatitude.setText(savedInstanceState.getString(LATITUDE_MSG));
//            tvLongitude.setText(savedInstanceState.getString(LONGITUDE_MSG));
//            tv_availability.setText(savedInstanceState.getString(LOCATION_AVAILABILITY));
            tvLatitude.setText(myLocation.getStrLatitude());
            tvLongitude.setText(myLocation.getStrLongitude());
            tv_availability.setText(myLocation.getStrLocationAvailability());
//        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();

            }
        });
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint){
        createLocationRequest();

        //updateLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    }
                return;
                }

            // other 'case' lines to check for other
            // permissions this app might request
            }
    }

    @Override
    public void onConnectionFailed(ConnectionResult var1){

    }

    @Override
    public void onConnectionSuspended(int var1){}

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        savedInstanceState.putDouble(LATITUDE, latitude);
//        savedInstanceState.putDouble(LONGITUDE, longitude);
//
//        savedInstanceState.putString(LATITUDE_MSG, tvLatitude.getText().toString());
//        savedInstanceState.putString(LONGITUDE_MSG, tvLongitude.getText().toString());
//
//        savedInstanceState.putString(LOCATION_AVAILABILITY, tv_availability.getText().toString());
//    }


    private void updateLocation(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

//        LocationAvailability availability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
//        if(availability.isLocationAvailable()){
//            tv_availability.setText("Available");
//        }
//        else{
//            tv_availability.setText("Not Available");
//        }

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            myLocation.setStrLocationAvailability("Not Available");
            tv_availability.setText("Not Available");
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationListener);
        }
        else{
            myLocation.setStrLocationAvailability("Available");
            tv_availability.setText("Available");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        double responseLongitude = 0.0;
        double responseLatitude = 0.0;
        if(location != null) {
            responseLongitude = location.getLongitude();
            responseLatitude = location.getLatitude();
        }

        String longitudeMsg = "";
        String latitudeMsg = "";
        //if(responseLatitude == latitude && responseLongitude == longitude){
        if(responseLatitude == myLocation.getLatitude() && responseLongitude == myLocation.getLongitude()){
            latitudeMsg = "Out of date: " + Double.toString(responseLatitude);
            myLocation.setStrLatitude(latitudeMsg);
            longitudeMsg = "Out of date: " + Double.toString(responseLongitude);
            myLocation.setStrLongitude(longitudeMsg);
        }
        else{
//            latitude = responseLatitude;
//            longitude = responseLongitude;
            myLocation.setLatitude(responseLatitude);
            myLocation.setLongitude(responseLongitude);

            latitudeMsg = Double.toString(responseLatitude);
            myLocation.setStrLatitude(latitudeMsg);
            longitudeMsg = Double.toString(responseLongitude);
            myLocation.setStrLongitude(longitudeMsg);
        }

        tvLongitude.setText(longitudeMsg);
        tvLatitude.setText(latitudeMsg);

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener(){
//            @Override
//            public void onLocationChanged(Location location){
//                if(location != null){
//                    double auto_longitude = location.getLongitude();
//                    double auto_latitude = location.getLatitude();
//                    tv_autoLongitude.setText(Double.toString(auto_longitude));
//                    tv_autoLatitude.setText(Double.toString(auto_latitude));
//                }
//            }
//        });

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);

//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//            .addLocationRequest(mLocationRequest);
//
//        PendingResult<LocationSettingsResult> pendingResult =
//        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//
//        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>(){
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                final LocationSettingsStates states = result.getLocationSettingsStates();
//                if(states.isGpsPresent()){
//
//                }
//                else{
//                    tv_availability.setText("GPS not present");
//                }
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can
//                        // initialize location requests here.
//
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied, but this can be fixed
//                        // by showing the user a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(
//                                    MainActivity.this,
//                                    REQUEST_CHECK_SETTINGS);
//                            } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                            }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way
//                        // to fix the settings so we won't show the dialog.
//
//                        break;
//                    }
//                }
//            });
    }

}
