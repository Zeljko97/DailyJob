package com.example.projekat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

//import javax.xml.transform.Result;


import javax.xml.transform.Result;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsTracker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private MapInterface mapInterface;
    private List<LatLng> polylinelist;
    private PolylineOptions polylineOptions;
    private com.google.android.gms.maps.model.LatLng origion, dest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tracker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maptracker);

        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com/")
                .build();
        mapInterface = retrofit.create(MapInterface.class);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        origion = new com.google.android.gms.maps.model.LatLng(30.7333,76.7794);
        dest = new com.google.android.gms.maps.model.LatLng(28.7041,77.1025);

        getDirection("30.7333" + "," + "76.7794", "28.7041" + "," + "77.1025");


    }

    private void getDirection(String origin, String destination){
        mapInterface.getDirection("driving","less_driving",origin,destination,
                getString(R.string.google_maps_key)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {

                    }


                    public void onSuccess(com.example.projekat.Result result) {
                        polylinelist = new ArrayList<>();
                        List<Route> routeList = result.getRoutes();
                        for(Route route : routeList){
                            String polyLine = route.getOverviewPolyline().getPoints();
                            polylinelist.addAll(decodePoly(polyLine));
                        }

                        polylineOptions = new PolylineOptions();
                        polylineOptions.color(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        polylineOptions.width(8);
                        polylineOptions.startCap(new ButtCap());
                        polylineOptions.jointType(JointType.ROUND);
                        polylineOptions.addAll(polylinelist);
                        map.addPolyline(polylineOptions);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(origion);
                        builder.include(dest);
                        map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),100));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private List<com.google.android.gms.maps.model.LatLng> decodePoly(String encoded) {

        List<com.google.android.gms.maps.model.LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while(index < len){
            int b, shift = 0, result = 0;
            do{
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;

            } while(b>=0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;

            do{
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while(b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            com.google.android.gms.maps.model.LatLng p = new com.google.android.gms.maps.model.LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}