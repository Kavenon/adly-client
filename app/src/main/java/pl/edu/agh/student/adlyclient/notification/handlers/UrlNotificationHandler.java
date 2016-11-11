package pl.edu.agh.student.adlyclient.notification.handlers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.notification.INotificationHandler;
import pl.edu.agh.student.adlyclient.notification.payload.RemotePayload;
import pl.edu.agh.student.adlyclient.notification.payload.UrlNotificationPayload;

public class UrlNotificationHandler implements INotificationHandler {

    @Override
    public void handle(RemotePayload remotePayload, Context context) {

        Uri url = Uri.parse(((UrlNotificationPayload) remotePayload.getPayload()).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
        else {
            Log.d(Constants.TAG, "Activity not found");
        }

    }

}
