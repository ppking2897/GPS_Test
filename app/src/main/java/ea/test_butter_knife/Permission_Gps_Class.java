package ea.test_butter_knife;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;


public class Permission_Gps_Class {
    private Activity activity;
    public Permission_Gps_Class(Context context){
        this.activity = activity;

    }
    public void checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(activity , Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity , new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION} , 123);
        }

    }

    public class requestPermission implements ActivityCompat.OnRequestPermissionsResultCallback{

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            for (int grantResult : grantResults){
                if (grantResult != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(activity , "請允許權限,否則無法定位" , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
