package pl.edu.agh.student.adlyclient;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdlyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(Constants.TAG, "Refreshed FCM token: " + token);

        String response = sendToken(UuidService.getInstance(getApplicationContext()).getUuid(), token);
        Log.d(Constants.TAG, "Send token response: " + response);

    }

    private String sendToken(String uuid, String token){
        try {
            Request request = new Request.Builder()
                    .url(Constants.TOKEN_REQUEST_URL + "?uuid=" + uuid + "&token=" + token)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.d(Constants.TAG, "Send token invalid response: " + e.getMessage());
        }
        return "exception occurred, check logs";
    }

}
