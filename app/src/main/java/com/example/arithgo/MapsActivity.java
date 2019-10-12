package com.example.arithgo;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arithgo.app.ArithApp;
import com.example.arithgo.model.entity.Student;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    private Polygon biblioteca;
    private Polygon bloqueC;
    private Polygon bloqueL;

    private static boolean isBiblioteca;
    private static boolean isC;
    private static boolean isL;

    private Marker location;

    private TextView data;

    private Button canje;
    private Button challenge;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        }, 11);
        data=findViewById(R.id.datos);
        Student es=ArithApp.getStudent();
        data.setText(es.toString());

        canje=findViewById(R.id.store_button);
        challenge=findViewById(R.id.challenge_button);
        logout=findViewById(R.id.logout_button);

        canje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,Store.class));
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,Challenge.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,LoginActivity.class));
            }
        });


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
        biblioteca=mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.341645, -76.529822),
                new LatLng(3.341814, -76.529817),
                new LatLng(3.341861, -76.530099),
                new LatLng(3.341676, -76.530107)
        ));

        bloqueC=mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.341192, -76.529894),
                new LatLng(3.341213, -76.530424),
                new LatLng(3.341074, -76.530419),
                new LatLng(3.341063, -76.529904)
        ));

        bloqueL=mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.340907, -76.529328),
                new LatLng(3.341338, -76.529304),
                new LatLng(3.341333, -76.529489),
                new LatLng(3.340926, -76.529502)
        ));

        LatLng ubic = new LatLng(3.340926, -76.529502);
        location=mMap.addMarker(new MarkerOptions().position(ubic).title("Mi Ubicación"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubic,15));

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        this.location.setPosition(pos);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));

         boolean isBibliotecaT= PolyUtil.containsLocation(pos,biblioteca.getPoints(),true);
         boolean isCT= PolyUtil.containsLocation(pos,bloqueC.getPoints(),true);
         boolean isLT= PolyUtil.containsLocation(pos,bloqueL.getPoints(),true);
         boolean vibrar=isBiblioteca^isBibliotecaT;
         vibrar^=isC&&isCT;
         vibrar^=!isL&&isLT;

         isBiblioteca=isBibliotecaT;
         isC=isCT;
         isL=isLT;
         canje.setEnabled(isBiblioteca);
         canje.setVisibility(!isBiblioteca?View.INVISIBLE:View.VISIBLE);

         challenge.setEnabled(isC||isL);
         challenge.setVisibility(!(isC||isL)?View.INVISIBLE:View.VISIBLE);

        if(vibrar){
            Vibrator v=(Vibrator) getSystemService(VIBRATOR_SERVICE);
            v.vibrate(2000);
        }


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
