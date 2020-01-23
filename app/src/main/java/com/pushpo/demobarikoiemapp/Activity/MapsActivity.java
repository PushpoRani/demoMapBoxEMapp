package com.pushpo.demobarikoiemapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.android.core.location.LocationEngine;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.pushpo.demobarikoiemapp.Adapter.NearbyPlacesListAdapter;
import com.pushpo.demobarikoiemapp.R;
import com.pushpo.demobarikoiemapp.Utils.ApiClient;
import com.pushpo.demobarikoiemapp.Utils.ApiInterface;
import com.pushpo.demobarikoiemapp.model.PlaceApiPojo;

import java.util.List;


public class MapsActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback {

    //private String Urls = "API_KEY/DISTANCE/LIMIT?longitude=TARGET_LONGITUDE&latitude=TARGET_LATITUDE&ptype=CATEGORY";

    private ApiInterface apiInterface;
    private String placeName;
    private Double placeLat, placeLng;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrolledOutItems;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView tvTitle;

    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Style style;
    private LocationComponent locationComponent;

    private static  final int REQUEST_LOCATION=1;
    private LocationManager locationManager;
    private String latitude,longitude;

    private NearbyPlacesListAdapter nearbyPlacesListAdapter;
    private final static String TAG = "MapsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //add permission
//        ActivityCompat.requestPermissions(this,new String[]
//                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            getLocation();
        }

        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        View bottomSheet = findViewById( R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(100);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style2) {

                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                map = mapboxMap;
                style = style2;

//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.title("Current Location");
//                markerOptions.position(new LatLng(locationComponent));
//                mapboxMap.addMarker(markerOptions);

//                IconFactory iconFactory = IconFactory.getInstance(MapsActivity.this);
//                Icon icon = iconFactory.fromResource(R.drawable.map_marker);
//
//                Double curlat = Double.valueOf(latitude);
//                Double curlng = Double.valueOf(longitude);
//
//                map.addMarker(new MarkerOptions()
//                        .position(new LatLng(curlat, curlng))
//                        .title(placeName)
//                        .icon(icon));

                enableLocationComponent();

                String placename = getIntent().getStringExtra("place");
                Log.d(TAG, "placename: " +placename);
                tvTitle.setText("List of Nearby " +placename);

//                Double currentLat = 23.837161365102958;
//                Double currentLng = 90.3667464852333;

                Double currentLat = Double.valueOf(latitude);
                Double currentLng = Double.valueOf(longitude);
                Log.d(TAG, "currentLat: " +currentLat);
                Log.d(TAG, "currentLng: " +currentLng);
                getNearbyPlaces(placename, currentLat, currentLng);
            }
        });
    }

    private void getNearbyPlaces(final String place, final Double lat, final Double lng){

		Log.d(TAG, "Success");
    	String ptype = place;
        Double latitude = lat;
        Double longitude = lng;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
		Log.d(TAG, "Api Interface: " +apiInterface);
        Call<PlaceApiPojo> call = apiInterface.getNearbyPlaces(ptype, latitude, longitude);
		Log.d(TAG, "Call: " +call);
        call.enqueue(new Callback<PlaceApiPojo>() {
            @Override
            public void onResponse(Call<PlaceApiPojo> call, Response<PlaceApiPojo> response) {
                try{
                    Log.d(TAG, "response_new: " + response.body());
                    if (response.isSuccessful() && response.body() != null){
                        List<PlaceApiPojo.Place> placeList = response.body().getPlace();
                        Log.d(TAG, String.valueOf(placeList.size()));

                        nearbyPlacesListAdapter = new NearbyPlacesListAdapter(getApplicationContext(), placeList);
                        recyclerView.setAdapter(nearbyPlacesListAdapter);
                        nearbyPlacesListAdapter.notifyDataSetChanged();
                        recyclerView.scheduleLayoutAnimation();

                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);

                                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                    isScrolling = true;
                                }
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

//										currentItems = mLayoutManager.getChildCount();
//										scrolledOutItems = mLayoutManager.findFirstVisibleItemPosition();
//										totalItems = mLayoutManager.getItemCount();

//                            Log.d(TAG, "currentItems: " + currentItems);
//                            Log.d(TAG, "scrolledOutItems: " + scrolledOutItems);
//                            Log.d(TAG, "totalItems: " + totalItems);
                                Log.d(TAG, "totalItems: " + totalItems);
                                Log.d(TAG, "isScrolling: " + isScrolling);

                                if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {
                                    //fetch new data
                                    isScrolling = false;

                                }
                            }
                        });


                        for (int i=0; i< placeList.size(); i++){
                            placeLat = response.body().getPlace().get(i).getLatitude();
                            placeLng = response.body().getPlace().get(i).getLongitude();
                            placeName = response.body().getPlace().get(i).getName();
                            Log.d(TAG, "Lat: " +placeLat);
                            Log.d(TAG, "Lng: " +placeLng);
                            Log.d(TAG, "Tittle: " +placeName);

                            IconFactory iconFactory = IconFactory.getInstance(MapsActivity.this);
                            Icon icon = iconFactory.fromResource(R.drawable.marker_places);

                            // Add the marker to the map
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(placeLat, placeLng))
                                    .title(placeName)
                                    .icon(icon));

                        }

                    }else{
						Log.d(TAG, "error: " + response.message());
                        if (response.code() == 401) {
							Log.i(TAG, "401");
                            final Dialog dialog = new Dialog(getApplicationContext());
                            dialog.setContentView(R.layout.custom_dialog_error);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            System.out.println("ScreenW: " + width + " ScreenH: " + height);
                            dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 6);

                            TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                            TextView tvMessage = dialog.findViewById(R.id.tvMessage);
                            Button btnOk = dialog.findViewById(R.id.btnOk);

                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            });

                            dialog.show();
							finish();
						}

						if (response.code() == 404) {
							Log.i(TAG, "404");
                            final Dialog dialog = new Dialog(getApplicationContext());
                            dialog.setContentView(R.layout.custom_dialog_error);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            System.out.println("ScreenW: " + width + " ScreenH: " + height);
                            dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 6);

                            TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                            TextView tvMessage = dialog.findViewById(R.id.tvMessage);
                            Button btnOk = dialog.findViewById(R.id.btnOk);

                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            });

                            dialog.show();
							//Toast.makeText(MapsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
						}
					}


                }catch (Exception e){
                    Log.d(TAG, "Something Wrong: " + e.getCause());

                }
            }

            @Override
            public void onFailure(Call<PlaceApiPojo> call, Throwable t) {

            }
        });

    }



    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent() {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent = map.getLocationComponent();

            Log.d("Map:", String.valueOf(locationComponent));

            // Activate with a built LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, style).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);


            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.NORMAL);


        } else {

            permissionsManager = new PermissionsManager(this);

            permissionsManager.requestLocationPermissions(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }




    void showSomethingOnTheMap()
    {
        // Do some work, create lat, lon, and name for point
        final double lat = 23.837161365102958;
        final double lon = 90.3667464852333;
        final String name = "Mirpur Dohs";
        // Ask MapsWithMe to show the point
        //MapsWithMeApi.showPointOnMap(this, lat, lon, name);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);


            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
