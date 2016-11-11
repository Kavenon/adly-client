package pl.edu.agh.student.adlyclient.notification.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.edu.agh.student.adlyclient.notification.NotificationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlNotification extends BaseNotification<UrlNotificationPayload> {

}
