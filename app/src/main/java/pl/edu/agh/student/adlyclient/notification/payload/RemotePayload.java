package pl.edu.agh.student.adlyclient.notification.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pl.edu.agh.student.adlyclient.notification.NotificationType;
import pl.edu.agh.student.adlyclient.notification.handlers.UrlNotificationHandler;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "U", value = UrlNotification.class),
        @JsonSubTypes.Type(name = "P", value = NoPayloadNotification.class),
        @JsonSubTypes.Type(name = "F", value = SurveyNotification.class),
})
public interface RemotePayload {

    NotificationType getType();
    Object getPayload();

}
