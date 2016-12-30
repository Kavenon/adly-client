package pl.edu.agh.student.adlyclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.helpers.SharedPreferenceHelper;

public class InputActivity extends Activity {

    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(Constants.TAG,"Set endpoint url: " + editText.getText().toString());
                if(editText.getText().toString().length() > 0){
                    SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),Constants.ADLY_URL_KEY, editText.getText().toString());

                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), SecondActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setAction("_adly_main" + System.currentTimeMillis());
                    getApplicationContext().startActivity(i);
                }

            }
        });

        fullScreen();

    }

    private void fullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
