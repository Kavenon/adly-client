package pl.edu.agh.student.adlyclient.notification.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.edu.agh.student.adlyclient.notification.NotificationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NoPayloadNotification implements RemotePayload {

    private NotificationType type;

    public void setType(NotificationType type) {
        this.type = type;
    }

    @Override
    public NotificationType getType() {
        return type;
    }

    @Override
    public Object getPayload() {
        return new Object();
    }
}
