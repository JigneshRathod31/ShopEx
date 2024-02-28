package com.jignesh.shopex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    int PG = PackageManager.PERMISSION_GRANTED;
    int REQ_CODE = 9;
    boolean flag = false;

    LocationManager locationManager;

    private String CITY;

    Button btnRegister;
    TextView tvLoginHere;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister = findViewById(R.id.btn_register);
        tvLoginHere = findViewById(R.id.tv_login_here);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            turnOnLocation();

        btnRegister.setOnClickListener(View -> {
            flag = getPermission();
            Toast.makeText(this, flag + "\n" + CITY, Toast.LENGTH_SHORT).show();

            if (flag) {
                if (ActivityCompat.checkSelfPermission(this,PERMISSIONS[0]) != PG
                        && ActivityCompat.checkSelfPermission(this, PERMISSIONS[1]) != PG)
                    ActivityCompat.requestPermissions(this,PERMISSIONS,REQ_CODE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, locationListener);
                if(CITY != null && flag) {
                    Log.d("CITY",CITY);
                    startActivity(new Intent(RegistrationActivity.this, userLogin.class));
                }
            }
        });

        tvLoginHere.setOnClickListener(View -> {
                startActivity(new Intent(RegistrationActivity.this, userLogin.class));
        });
    }

    private boolean getPermission(){
        if(ContextCompat.checkSelfPermission(this,PERMISSIONS[0]) != PG
                && ContextCompat.checkSelfPermission(this,PERMISSIONS[1]) != PG)
            ActivityCompat.requestPermissions(RegistrationActivity.this,PERMISSIONS, REQ_CODE);
        else
            return true;
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE)
            if(grantResults.length > 0)
                if(grantResults[0] == PG && grantResults[1] == PG)
                    Toast.makeText(this, "Location permission allowed", Toast.LENGTH_SHORT).show();
    }

    private void turnOnLocation(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        flag = false;
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

 /***
 * Current City
 * Sujal Balar
 * ***/

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Geocoder geocoder = new Geocoder(RegistrationActivity.this, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                CITY = addressList.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}