package pl.edu.agh.student.adlyclient.notification.handlers;

import android.content.Intent;

import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.helpers.JacksonHelper;
import pl.edu.agh.student.adlyclient.activity.SurveyActivity;
import pl.edu.agh.student.adlyclient.notification.INotificationHandler;
import pl.edu.agh.student.adlyclient.survey.Survey;

public class SurveyFormNotificationHandler extends RemotePayloadDependendHandler implements INotificationHandler {

    @Override
    protected void handlePayload(Object object, String responseJson) {

        Intent i = new Intent();
        i.setClass(context, SurveyActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction("_adly_surveycode" + System.currentTimeMillis());
        i.putExtra(Constants.SURVEY_OBJ_EXTRAS_KEY,JacksonHelper.toString((Survey)object));

        context.startActivity(i);

    }

    @Override
    protected void handleError(String responseJson) {
        // do nothing
    }

    @Override
    protected Class getModelClass() {
        return Survey.class;
    }

}
