package pl.edu.agh.student.adlyclient.notification;

import android.content.Context;

import pl.edu.agh.student.adlyclient.notification.payload.RemotePayload;

public interface INotificationHandler {

    void handle(RemotePayload remotePayload, Context context);

}
