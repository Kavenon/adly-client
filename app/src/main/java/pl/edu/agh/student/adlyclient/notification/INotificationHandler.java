package pl.edu.agh.student.adlyclient.notification;

import android.content.Context;

public interface INotificationHandler {

    void handle(Notification notification, Context context);

}
