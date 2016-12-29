package pl.edu.agh.student.adlyclient.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.helpers.AdlyUrlHelper;
import pl.edu.agh.student.adlyclient.notification.handlers.SimpleNotificationHandler;
import pl.edu.agh.student.adlyclient.notification.handlers.SurveyFormNotificationHandler;
import pl.edu.agh.student.adlyclient.notification.handlers.UrlNotificationHandler;
import pl.edu.agh.student.adlyclient.notification.payload.RemotePayload;

public class NotificationClickedReceiver extends BroadcastReceiver {


    private static final ObjectMapper om = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();
    private Context context;

    private static Map<NotificationType, INotificationHandler> handlers = new HashMap<>();
    static {
        handlers.put(NotificationType.P, new SimpleNotificationHandler());
        handlers.put(NotificationType.U, new UrlNotificationHandler());
        handlers.put(NotificationType.F, new SurveyFormNotificationHandler());
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        Notification notification = (Notification) intent.getSerializableExtra(Constants.NOTIFICATION_OBJ_EXTRAS_KEY);
        new IOAsyncTask().execute(notification.getId());

    }

    private class IOAsyncTask extends AsyncTask<Long, Void, String> {

        @Override
        protected String doInBackground(Long... params) {
            return getPayload(params[0]);
        }

        private String getPayload(Long notificationId) {
            try {
                Request request = new Request.Builder()
                        .url(AdlyUrlHelper.getEndpoint(context) + Constants.PAYLOAD_REQUEST_URL + "?_anid=" + notificationId)
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
                handlePayload(om.readValue(responseJson, RemotePayload.class),responseJson);
            } catch (IOException e) {
                Log.e(Constants.TAG, "Could not parse payload " + responseJson);
                handleError(responseJson);
            }
        }

    }

    private void handlePayload(RemotePayload remotePayload, String responseJson) {

        if(remotePayload.getType() == null){
            Log.i(Constants.TAG, "Notification without type skipping " + responseJson);
            return;
        }

        INotificationHandler iNotificationHandler = handlers.get(remotePayload.getType());
        if(iNotificationHandler == null){
            Log.e(Constants.TAG, "Notification handler not found " + remotePayload.getType());
        }
        else {
            iNotificationHandler.handle(remotePayload,context);
        }

    }

    private void handleError(String responseJson) {
        Log.e(Constants.TAG, "Notification remote payload error " + responseJson);
    }

}
