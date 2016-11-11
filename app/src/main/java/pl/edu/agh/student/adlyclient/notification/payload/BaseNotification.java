package pl.edu.agh.student.adlyclient.notification.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.edu.agh.student.adlyclient.notification.NotificationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseNotification<T> implements RemotePayload {

    private NotificationType type;
    private T payload;

    public void setType(NotificationType type) {
        this.type = type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public NotificationType getType() {
        return type;
    }
}
