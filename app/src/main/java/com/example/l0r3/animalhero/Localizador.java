package com.example.l0r3.animalhero;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.example.l0r3.animalhero.R;

/**
 * Created by CASA on 25/03/2017.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mapa;
    private GoogleApiClient client;

    public Localizador(Context context, GoogleMap mapa) {
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
        client.connect();
        this.mapa = mapa;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50);
        request.setInterval(100);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
//        CameraUpdate cam = new CameraUpdateFactory.newLatLng(pos);
//        mapa.moveCamera(cam);

    }
}
