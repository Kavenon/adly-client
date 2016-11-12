package pl.edu.agh.student.adlyclient.config;

public interface Constants {

    String TAG = "Adly";

    String UUID_REQUEST_URL = "/device/register";
    String TOKEN_REQUEST_URL = "/device/token";

    String PAYLOAD_REQUEST_URL = "/rule/notification/payload";

    String SURVEY_URL = "/profile/survey/response";

    String BEACON_SYNC_REQUEST_URL = "/beacon/discover";

    String NOTIFICATION_OBJ_EXTRAS_KEY = "_adly_nobj";
    String SURVEY_OBJ_EXTRAS_KEY = "_adly_sobj";

    String SURVEY_CLIENT_URL = "/profile/survey/client";
    Integer welcomeSurveyId = 32; // todo: read system surveys?
    String WELCOME_SURVEY_STAT = "adly.welcome.survey.stat";
}
