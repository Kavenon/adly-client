package pl.edu.agh.student.adlyclient.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import pl.edu.agh.student.adlyclient.Constants;
import pl.edu.agh.student.adlyclient.JacksonHelper;
import pl.edu.agh.student.adlyclient.MainActivity;
import pl.edu.agh.student.adlyclient.R;

public class NotificationCreator {

    private Context context;

    public NotificationCreator(Context context) {
        this.context = context;
    }

    public void send(RemoteMessage message){

        Log.i(Constants.TAG, "Received push data: " + message.getData());

        Notification notification = JacksonHelper.fromMap(message.getData(), Notification.class);
        PendingIntent pendingIntent = createIntent(notification);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getText())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private PendingIntent createIntent(Notification value) {
        Intent actionIntent = new Intent(context, NotificationClickedReceiver.class);
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actionIntent.putExtra(Constants.NOTIFICATION_OBJ_EXTRAS_KEY, value);
        actionIntent.setAction("_adly_action_" + System.currentTimeMillis());
        return PendingIntent.getBroadcast(context, 0, actionIntent, 0);
    }
}
