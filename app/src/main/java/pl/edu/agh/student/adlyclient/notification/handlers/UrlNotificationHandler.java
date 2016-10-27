package pl.edu.agh.student.adlyclient.notification.handlers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import pl.edu.agh.student.adlyclient.Constants;
import pl.edu.agh.student.adlyclient.notification.INotificationHandler;
import pl.edu.agh.student.adlyclient.notification.Notification;
import pl.edu.agh.student.adlyclient.notification.payload.UrlNotificationPayload;

public class UrlNotificationHandler extends RemotePayloadDependendHandler implements INotificationHandler {

    @Override
    protected void handlePayload(Object object, String responseJson) {

        Uri url = Uri.parse(((UrlNotificationPayload) object).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
        else {
            Log.d(Constants.TAG, "Activity not found");
        }

    }

    @Override
    protected void handleError(String responseJson) {
        // do nothing
    }

    @Override
    protected Class getModelClass() {
        return UrlNotificationPayload.class;
    }

}
