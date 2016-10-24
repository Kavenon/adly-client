package pl.edu.agh.student.adlyclient;

import android.content.Context;
import android.os.AsyncTask;

import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UuidService {

    private static class SingletonHolder {
        public static UuidService instance = null;
        public static UuidService getInstance(Context context){
            if (null == instance) {
                instance = new UuidService(context);
            }
            return instance;
        }
    }

    public static UuidService getInstance(Context context){
        return SingletonHolder.getInstance(context);
    }

    private static final String UUID_KEY = "adly.uuid";
    private final OkHttpClient client = new OkHttpClient();

    private Context context;
    private String uuid;

    private UuidService(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        String storedUuid = SharedPreferenceHelper.getSharedPreferenceString(context, UUID_KEY, null);

        if(storedUuid == null || storedUuid.length() == 0){
            uuid = generateNewUuid();
            SharedPreferenceHelper.setSharedPreferenceString(context, UUID_KEY, uuid);
            new IOAsyncTask().execute(uuid);
            Log.d(Constants.TAG, "Generated new uuid: " + uuid);
        }
        else {
            uuid = storedUuid;
            Log.d(Constants.TAG, "Old uuid used: " + uuid);
        }
    }

    private String generateNewUuid() {
        return UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    private class IOAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return sendUuid(params[0]);
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d(Constants.TAG, "Register uuid success response: " + response);
        }

    }

    private String sendUuid(String uuid){
        try {
            Request request = new Request.Builder()
                    .url(Constants.UUID_REQUEST_URL + "?uuid=" + uuid)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.d(Constants.TAG, "Register uuid invalid response: " + e.getMessage());
        }
        return "exception occured, check logs";
    }

}
