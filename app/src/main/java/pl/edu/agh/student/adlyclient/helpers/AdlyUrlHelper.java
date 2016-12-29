package pl.edu.agh.student.adlyclient.helpers;

import android.content.Context;

import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.config.Constants;

public class AdlyUrlHelper {

    public static String getEndpoint(Context context){

        return SharedPreferenceHelper
                .getSharedPreferenceString(
                        context,
                        Constants.ADLY_URL_KEY,
                        context.getString(R.string.adly_url));

    }
}
