package pl.edu.agh.student.adlyclient.config;

public interface Constants {

    String TAG = "Adly";

    String UUID_REQUEST_URL = "/api/device/register";
    String TOKEN_REQUEST_URL = "/api/device/token";

    String PAYLOAD_REQUEST_URL = "/api/notification/payload";

    String SURVEY_URL = "/api/survey/response";

    String BEACON_SYNC_REQUEST_URL = "/api/beacon/discover";

    String NOTIFICATION_OBJ_EXTRAS_KEY = "_adly_nobj";
    String SURVEY_OBJ_EXTRAS_KEY = "_adly_sobj";
}
