package pl.edu.agh.student.adlyclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import pl.edu.agh.student.adlyclient.beacon.BeaconMonitorService;
import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.UuidService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fullScreen();
        permissions();

        UuidService.getInstance(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void fullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void permissions() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,getApplicationContext(),MainActivity.this)) {
            Intent intent = new Intent(this, BeaconMonitorService.class);
            startService(intent);
        }
        else {
            requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,0,getApplicationContext(),MainActivity.this);
        }
    }

    public void requestPermission(String strPermission, int perCode, Context _c, Activity _a){

        if (ActivityCompat.shouldShowRequestPermissionRationale(_a,strPermission)){
            Toast.makeText(getApplicationContext(),"Location permission required for beacons",Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(_a,new String[]{strPermission},perCode);
        }

    }

    public boolean checkPermission(String strPermission,Context _c,Activity _a){
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
