package pl.edu.agh.student.adlyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    public static MainActivity get() { return instance; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UuidService.getInstance(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(Constants.TAG, "token: " + token);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(Constants.TAG, "Key: " + key + " Value: " + value);
            }
        }

    }
}
