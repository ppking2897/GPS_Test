package ea.test_butter_knife;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class GPS_Status_Class {
    private LocationManager mlocationManager;
    private Activity activity;
    private GpsStatus.Listener gpsStatusListener;
    private LocationListener locationListener;
    private String locationProvider;
    private String textStatus;
    private TextView lookView;

    public  GPS_Status_Class(Activity activity , TextView lookView){
        this.activity = activity;
        this.lookView = lookView;
        mlocationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = mlocationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {

            locationProvider = LocationManager.GPS_PROVIDER;


        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {

            locationProvider = LocationManager.NETWORK_PROVIDER;

        } else {

            Toast.makeText(activity, "請打開網路以及GPS定位，以便找尋你的位置", Toast.LENGTH_SHORT).show();

        }

        setListener();

        if (!mlocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(activity , "請開啟GPS" , Toast.LENGTH_SHORT).show();
        }else{
            mlocationManager.addGpsStatusListener(gpsStatusListener);

            mlocationManager.requestLocationUpdates(locationProvider , 10000 , 0 , locationListener);

        }

    }

    private void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    private void setListener(){
         gpsStatusListener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int i) {
                switch (i){
                    //定位啟動
                    case GpsStatus.GPS_EVENT_STARTED:
                        Log.v("ppking" , "GPS_EVENT_STARTED :"+GpsStatus.GPS_EVENT_STARTED);
                        textStatus = "GPS_EVENT_STARTED\n";
                        break;
                    //定未結束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        Log.v("ppking" , "GPS_EVENT_STOPPED :"+GpsStatus.GPS_EVENT_STOPPED);
                        textStatus = "GPS_EVENT_STOPPED\n";
                        break;
                    //第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.v("ppking" , "GPS_EVENT_FIRST_FIX :"+GpsStatus.GPS_EVENT_FIRST_FIX);
                        textStatus = "GPS_EVENT_FIRST_FIX\n";
                        break;
                    //衛星狀態改變
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.v("ppking" , "GPS_EVENT_SATELLITE_STATUS :"+GpsStatus.GPS_EVENT_SATELLITE_STATUS);
                        textStatus = "GPS_EVENT_SATELLITE_STATUS\n";
                        break;
                }
                lookView.append(textStatus);

            }
        };


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("ppking" , "Location Latitude : " + location.getLatitude()  );
                Log.v("ppking" , "Location Longitude : " + location.getLongitude()  );

                textStatus = "Location Latitude : " + location.getLatitude()+"\n";
                textStatus += "Location Longitude : " + location.getLongitude()+"\n";
                lookView.append(textStatus);
                //取得經緯度
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                switch (i){

                    //GPS狀態為服務區外
                    case LocationProvider.OUT_OF_SERVICE :
                        Log.v("ppking" , "LocationProvider.OUT_OF_SERVICE : " +LocationProvider.OUT_OF_SERVICE);
                        textStatus += "LocationProvider.OUT_OF_SERVICE\n";
                        break;
                    //GPS狀態為暫停服務
                    case LocationProvider.TEMPORARILY_UNAVAILABLE :
                        Log.v("ppking" , "LocationProvider.TEMPORARILY_UNAVAILABLE : " + LocationProvider.TEMPORARILY_UNAVAILABLE);
                        textStatus += "LocationProvider.TEMPORARILY_UNAVAILABLE \n";
                        break;
                    //GPS為可見狀態
                    case LocationProvider.AVAILABLE :
                        Log.v("ppking" , "LocationProvider.AVAILABLE  : " +LocationProvider.AVAILABLE );
                        textStatus +="LocationProvider.AVAILABLE\n   ";
                        break;

                }
                lookView.append(textStatus);

            }

            @Override
            public void onProviderEnabled(String s) {


                Log.v("ppking" , "onProviderEnabled  : " +s );
                textStatus ="LocationProvider.AVAILABLE : " +s +"\n";
                lookView.append(textStatus);

            }

            @Override
            public void onProviderDisabled(String s) {

                Log.v("ppking" , "onProviderDisabled  : " +s );
                textStatus ="onProviderDisabled : " +s +"\n";
                lookView.append(textStatus);
            }
        };
    }
}
