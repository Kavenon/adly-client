package pl.edu.agh.student.adlyclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import pl.edu.agh.student.adlyclient.beacon.BeaconMonitorService;
import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.UuidService;
import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.helpers.SharedPreferenceHelper;
import pl.edu.agh.student.adlyclient.survey.WelcomeSurveyService;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fullScreen();
        permissions();

        UuidService.getInstance(getApplicationContext());

        String storedUuid = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(), "adly.uuid", null);

        if(storedUuid != null && !SharedPreferenceHelper.getSharedPreferenceBoolean(getApplicationContext(), Constants.WELCOME_SURVEY_STAT, false)){
            WelcomeSurveyService.execute(getApplicationContext());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(Constants.TAG, "Current FCM token: " + token);

    }

    private void fullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void permissions() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,getApplicationContext(),SecondActivity.this)) {
            Intent intent = new Intent(this, BeaconMonitorService.class);
            startService(intent);
        }
        else {
            requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,0,getApplicationContext(),SecondActivity.this);
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
