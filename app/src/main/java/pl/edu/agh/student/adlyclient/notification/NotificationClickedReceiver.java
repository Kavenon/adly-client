package pl.edu.agh.student.adlyclient.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.notification.handlers.SimpleNotificationHandler;
import pl.edu.agh.student.adlyclient.notification.handlers.SurveyFormNotificationHandler;
import pl.edu.agh.student.adlyclient.notification.handlers.UrlNotificationHandler;

public class NotificationClickedReceiver extends BroadcastReceiver {

    private static Map<NotificationType, INotificationHandler> handlers = new HashMap<>();
    static {
        handlers.put(NotificationType.P, new SimpleNotificationHandler());
        handlers.put(NotificationType.U, new UrlNotificationHandler());
        handlers.put(NotificationType.S, new SurveyFormNotificationHandler());
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        Notification notification = (Notification) intent.getSerializableExtra(Constants.NOTIFICATION_OBJ_EXTRAS_KEY);

        INotificationHandler notificationHandler = handlers.get(notification.getType());
        if(notificationHandler != null){
            notificationHandler.handle(notification,context);
        }
        else {
            Log.d(Constants.TAG, "Could not find notification handler for type: " + notification.getType().toString());
        }

    }

}
