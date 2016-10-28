package pl.edu.agh.student.adlyclient.networking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.edu.agh.student.adlyclient.Constants;

public class AsyncGet {

    private static final OkHttpClient client = new OkHttpClient();

    public static void execute(Request request){

        new AsyncTask<Request, Void, String>(){

            @Override
            protected String doInBackground(Request... params) {
                return send(params[0]);
            }

            @Override
            protected void onPostExecute(String response) {
                Log.d(Constants.TAG, "Register uuid success response: " + response);
            }

        }.execute(request);

    }

    private static String send(Request request){
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.d(Constants.TAG, "Async get invalid response: " + e.getMessage());
        }
        return null;
    }
}
