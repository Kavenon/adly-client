package pl.edu.agh.student.adlyclient.notification.handlers;

import android.content.Context;

import java.lang.reflect.InvocationHandler;

import pl.edu.agh.student.adlyclient.notification.INotificationHandler;
import pl.edu.agh.student.adlyclient.notification.Notification;

public class SimpleNotificationHandler implements INotificationHandler {

    @Override
    public void handle(Notification notification, Context context) {
        // do nothing
    }

}
