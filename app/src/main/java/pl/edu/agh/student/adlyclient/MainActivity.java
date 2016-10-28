package pl.edu.agh.student.adlyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.kontakt.sdk.android.common.KontaktSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = new Intent(this, BeaconMonitorService.class);
        startService(intent);

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

        Button button=(Button)findViewById(R.id.surveyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SurveyActivity.class);
                startActivity(i);
            }
        });

    }
}
