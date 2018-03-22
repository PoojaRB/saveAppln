package com.example.nirma.sos2;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    //String phNo;
    Button sendBtn;
    TextView textView;
    public String s,t;
    String userLocation;
    int cnt=1;
    LocationManager locationManager,locMan1;
    public List <String> a1 = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocation();
        sendBtn = (Button) findViewById(R.id.button);
        textView=(TextView) findViewById(R.id.textView);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getLocation();

            }
        });
    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        //tv1.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        s = Double.toString(location.getLatitude());
        t = Double.toString(location.getLongitude());
        cnt = 2;

        //tv1.setText(Double.toString(location.getLatitude()));
        //tv2.setText(Double.toString(location.getLongitude()));
        try {
            Toast.makeText(getApplicationContext(),"HEllo location changed",Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            userLocation="My current address is: "+addresses.get(0).getAddressLine(0);
            textView.setText("Your current address is: " +"\n"+addresses.get(0).getAddressLine(0));
            sendMessage();
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
         }

    public void sendMessage(){

        a1.add("8939212329");
        a1.add("9442421491");
        a1.add("9790654474");
        a1.add("9789950987");
        a1.add("9677207193");
        // Toast.makeText(getApplicationContext(),"HEllooo",Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }else {
            SmsManager smsManager = SmsManager.getDefault();
            for (String phNo:a1) {

                smsManager.sendTextMessage(phNo, null, "Hello, contact  me soon. My current location is :\n" + userLocation, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            }
        }
    }
    //Below function is executed only if sms is not sent in previous function:
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    SmsManager smsManager = SmsManager.getDefault();
                    for (String phNo:a1) {

                        smsManager.sendTextMessage(phNo, null, "Hello, contact  me soon. My current location is :\n Latitude:"+s+"\nLongitude:"+t+"\n" + userLocation, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}
