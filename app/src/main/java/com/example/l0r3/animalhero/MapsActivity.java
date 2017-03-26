package com.example.l0r3.animalhero;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.l0r3.animalhero.dao.HeroDAO;
import com.example.l0r3.animalhero.modelo.Hero;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "mapaTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public LatLng pegaCoordenadasPorEndereco(String endereco) throws IOException {
        Geocoder geo = new Geocoder(this);
        List<Address> listaEnderecos = geo.getFromLocationName(endereco, 1);
        LatLng latLng = new LatLng(listaEnderecos.get(0).getLatitude(), listaEnderecos.get(0).getLongitude());

        return latLng;
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


        HeroDAO dao = new HeroDAO(this);
        for (Hero hero : dao.buscaHeros()) {
            Log.d(TAG, "hero:" + hero.getEndereco());
            LatLng posicaoHero = null;
            try {
                Log.d(TAG, "try:");
                posicaoHero = pegaCoordenadasPorEndereco(hero.getEndereco());
            } catch (IOException e) {
                Log.d(TAG, "catch:");
                e.printStackTrace();
            }
            MarkerOptions mark = new MarkerOptions().position(posicaoHero).title(hero.getNome());
            Log.d(TAG, "mark:" + mark);
            mark.snippet(String.valueOf(hero.getNota()));
            mMap.addMarker(mark);
        }
        dao.close();

        new Localizador(this, mMap);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
            Log.d("GPS", "GPS Enabled");
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "posatual:" + location);
        }
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            Log.d(TAG, "posatual:" + cameraPosition);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }
}
class MyLocationListener implements android.location.LocationListener {

    public void onLocationChanged(Location loc) {
        String longi = "" + loc.getLongitude();
        String lat = "" + loc.getLatitude();
        Log.d("listener", loc.toString());
    }

    public void onStatusChanged(String s, int i, Bundle b) {
        Log.d("listener", "Provider status changed");
    }

    public void onProviderDisabled(String s) {
        Log.d("listener", "Provider disabled by the user. GPS turned off");
    }

    public void onProviderEnabled(String s) {
        Log.d("listener","Provider enabled by the user. GPS turned on");
    }

}
