package pl.edu.agh.student.adlyclient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormFragment;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.edu.agh.student.adlyclient.survey.EnumeratedPropertyType;
import pl.edu.agh.student.adlyclient.survey.FormBuilder;
import pl.edu.agh.student.adlyclient.survey.PropertyType;
import pl.edu.agh.student.adlyclient.survey.SimplePropertyType;
import pl.edu.agh.student.adlyclient.survey.Survey;
import pl.edu.agh.student.adlyclient.survey.SurveyField;
import pl.edu.agh.student.adlyclient.survey.SurveyFieldResponse;
import pl.edu.agh.student.adlyclient.survey.SurveyResponse;
import pl.edu.agh.student.adlyclient.survey.type.GenderPropertyEnum;

public class SurveyActivity extends AppCompatActivity {

    private static final String FORM_FRAGMENT_KEY = "adly_survey_form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        try {
            Survey survey = JacksonHelper.fromString(getIntent().getStringExtra(Constants.SURVEY_OBJ_EXTRAS_KEY),Survey.class);
            SurveyFormFragment formFragment = handleRetainedFragment(survey);
            setSubmitAction(formFragment);
        }
        catch (IOException e){
            finish();
        }

    }

    private Survey getMockJsonForm() {
        List<SurveyField> fields = new ArrayList<>();
        fields.add(new SurveyField(1,"name", new SimplePropertyType(PropertyType.TEXT)));
        fields.add(new SurveyField(2,"gender", new EnumeratedPropertyType(GenderPropertyEnum.stringNames())));

        Survey survey = new Survey();
        survey.setSurveyId(1);
        survey.setFieldList(fields);


        return survey;
    }

    @NonNull
    private SurveyFormFragment handleRetainedFragment(Survey surveyJson) {
        SurveyFormFragment formFragment;Fragment retainedFragment = getSupportFragmentManager().findFragmentByTag(FORM_FRAGMENT_KEY);
        if (retainedFragment != null && retainedFragment instanceof SurveyFormFragment) {
            formFragment = (SurveyFormFragment) retainedFragment;
        } else {
            formFragment = SurveyFormFragment.newInstance(surveyJson);
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, formFragment, FORM_FRAGMENT_KEY)
                .commit();
        }
        return formFragment;
    }

    private void setSubmitAction(final SurveyFormFragment formFragment) {
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFragment.submitSurvey();
                finish();
            }
        });
    }

    public static class SurveyFormFragment extends FormFragment {

        public static final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        private final OkHttpClient client = new OkHttpClient();
        public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        private Survey survey;

        public static SurveyFormFragment newInstance(Survey surveyJson){
            SurveyFormFragment f = new SurveyFormFragment();
            Bundle bdl = new Bundle(1);
            bdl.putSerializable(Constants.SURVEY_OBJ_EXTRAS_KEY, surveyJson);
            f.setArguments(bdl);
            return f;
        }

        public boolean submitSurvey() {

            if(survey != null){

                SurveyResponse surveyResponse = new SurveyResponse();
                surveyResponse.setUuid(UuidService.getInstance(getContext()).getUuid());
                surveyResponse.setSurveyId(survey.getSurveyId());
                surveyResponse.setFieldList(buildFieldListResponse());

                new IOAsyncTask().execute(surveyResponse);

                return true;
            }
            else {
                return false;
            }

        }

        private class IOAsyncTask extends AsyncTask<SurveyResponse, Void, String> {

            @Override
            protected String doInBackground(SurveyResponse... params) {
                return sendSurvey(params[0]);
            }

            @Override
            protected void onPostExecute(String response) {
                Log.d(Constants.TAG, "Send survey success response: " + response);
            }

        }

        private String sendSurvey(SurveyResponse param) {

            try {
                RequestBody formBody = RequestBody.create(JSON, OBJECT_MAPPER.writeValueAsString(param));
                Request request = buildRequest(formBody);
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response);
                }
                return request.body().toString();

            }
            catch(Exception e){
                Log.e(Constants.TAG, "Survey post error",e);
                return "error check logs";
            }

        }


        private List<SurveyFieldResponse> buildFieldListResponse() {
            ArrayList<SurveyFieldResponse> fieldList = new ArrayList<>();
            for (SurveyField surveyField : survey.getFieldList()) {
                String fieldId = String.valueOf(surveyField.getFieldId());
                String value = (String) getModel().getValue(fieldId);
                fieldList.add(new SurveyFieldResponse(surveyField.getFieldId(), value));
            }
            return fieldList;
        }

        private Request buildRequest(RequestBody requestBody) {
            return new Request.Builder()
                                .url(Constants.SURVEY_URL)
                                .post(requestBody)
                                .build();
        }



        @Override
        public void initForm(FormController controller) {

            survey = (Survey) getArguments().getSerializable(Constants.SURVEY_OBJ_EXTRAS_KEY);
            FormSectionController build = FormBuilder
                    .aBuilder()
                    .withContext(getContext())
                    .withSurvey(survey)
                    .build();
            controller.addSection(build);

        }
    }
}