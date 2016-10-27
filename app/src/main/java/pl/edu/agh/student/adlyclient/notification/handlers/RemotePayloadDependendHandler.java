package pl.edu.agh.student.adlyclient.notification.handlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.edu.agh.student.adlyclient.Constants;
import pl.edu.agh.student.adlyclient.notification.Notification;

public abstract class RemotePayloadDependendHandler {

    private static final ObjectMapper om = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();
    protected Context context;

    protected void fetchPayload(Notification notification){
        new IOAsyncTask().execute(notification.getId());
    }

    public void handle(Notification notification, Context context) {
        fetchPayload(notification);
        this.context = context;
    }

    protected abstract void handlePayload(Object object, String responseJson);

    private class IOAsyncTask extends AsyncTask<Long, Void, String> {

        @Override
        protected String doInBackground(Long... params) {
            return getPayload(params[0]);
        }

        private String getPayload(Long notificationId) {
            try {
                Request request = new Request.Builder()
                        .url(Constants.PAYLOAD_REQUEST_URL + "?_anid=" + notificationId)
                        .get()
                        .build();

                Response response = client.newCall(request).execute();

                if(response.code() == 200){
                    return response.body().string();
                }
                else {
                    throw new IOException("Could not fetch payload, server did not responded with 200 code" + response.body().string());
                }

            } catch (IOException e) {
                Log.e(Constants.TAG, "Payload invalid response: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String responseJson) {
            Log.d(Constants.TAG, "Payload success response: " + responseJson);

            if(responseJson == null || responseJson.length() == 0){
                handleError(responseJson);
            }

            try {
                handlePayload(om.readValue(responseJson, getModelClass()),responseJson);
            } catch (IOException e) {
                Log.e(Constants.TAG, "Could not parse payload " + responseJson);
                handleError(responseJson);
            }
        }

    }

    protected abstract void handleError(String responseJson);

    protected abstract Class getModelClass();

}
