package pl.edu.agh.student.adlyclient.notification.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.edu.agh.student.adlyclient.survey.Survey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyNotification extends BaseNotification<Survey> {

}
