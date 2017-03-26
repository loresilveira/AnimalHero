package com.example.l0r3.animalhero;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CASA on 25/03/2017.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

  public static final int REQUEST_CODE_PERM_LOCATION = 12345;
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
      if (ActivityCompat.checkSelfPermission(client.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED &&
          ActivityCompat.checkSelfPermission(client.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions((Activity) client.getContext(),
              new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERM_LOCATION);
      } else {
          LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
      }
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
