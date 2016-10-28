package pl.edu.agh.student.adlyclient.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import pl.edu.agh.student.adlyclient.notification.NotificationCreator;

import static pl.edu.agh.student.adlyclient.config.Constants.TAG;

public class AdlyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationCreator notificationCreator;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationCreator = new NotificationCreator(getApplicationContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        try {
            notificationCreator.send(remoteMessage);
        }
        catch(Exception e){
            Log.e(TAG, "Could not create notification",e);
        }
    }

}
