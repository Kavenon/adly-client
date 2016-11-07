package pl.edu.agh.student.adlyclient.survey;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.activity.SurveyActivity;
import pl.edu.agh.student.adlyclient.config.Constants;

public class WelcomeSurveyService {

    private final static OkHttpClient client = new OkHttpClient();

    public static void execute(final Context context){
        new AsyncTask<Integer, Void, String>(){

            @Override
            protected String doInBackground(Integer... params) {

                try {
                    Request request = new Request.Builder()
                            .url(context.getString(R.string.adly_url) + Constants.SURVEY_CLIENT_URL +
                                    "?id=" + params[0])
                            .build();
                    Response response = client.newCall(request).execute();

                    if(response.code() == 200){
                        return response.body().string();
                    }
                    else {
                        Log.e(Constants.TAG, "Could not get welcome survey" + response.toString());
                        return null;
                    }

                }
                catch(Exception e){
                    Log.e(Constants.TAG, "Could not get welcome survey");
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String surveyJson) {
                if(surveyJson != null){
                    Intent i = new Intent();
                    i.setClass(context, SurveyActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setAction("_adly_surveycode" + System.currentTimeMillis());
                    i.putExtra(Constants.SURVEY_OBJ_EXTRAS_KEY, surveyJson);
                    context.startActivity(i);
                    Log.d(Constants.TAG, "Success response: " + surveyJson);
                }

            }

        }.execute(Constants.welcomeSurveyId);

    }
}
