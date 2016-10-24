package pl.edu.agh.student.adlyclient;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormFragment;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.adlyclient.survey.EnumeratedPropertyType;
import pl.edu.agh.student.adlyclient.survey.FormBuilder;
import pl.edu.agh.student.adlyclient.survey.PropertyType;
import pl.edu.agh.student.adlyclient.survey.SimplePropertyType;
import pl.edu.agh.student.adlyclient.survey.Survey;
import pl.edu.agh.student.adlyclient.survey.SurveyField;
import pl.edu.agh.student.adlyclient.survey.type.GenderPropertyEnum;

public class SurveyActivity extends FragmentActivity {

    private static final String FORM_FRAGMENT_KEY = "adly_survey_form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        /*
            @todo: get object from extras (which was previously downloaded from adly endpoint)
        */
        // ============== TO DELETE ==========================
        String surveyJson = getMockJsonForm();
        // ============== TO DELETE ==========================

        SurveyFormFragment formFragment = handleRetainedFragment(surveyJson);
        setSubmitAction(formFragment);


    }

    private String getMockJsonForm() {
        List<SurveyField> fields = new ArrayList<>();
        fields.add(new SurveyField(1,"name", new SimplePropertyType(PropertyType.TEXT)));
        fields.add(new SurveyField(2,"gender", new EnumeratedPropertyType(GenderPropertyEnum.stringNames())));

        Survey survey = new Survey();
        survey.setSurveyId(1);
        survey.setFieldList(fields);

        ObjectMapper om = new ObjectMapper();
        String surveyJson = "";
        try {
            surveyJson = om.writeValueAsString(survey);
        } catch (JsonProcessingException e) {
        }
        return surveyJson;
    }

    @NonNull
    private SurveyFormFragment handleRetainedFragment(String surveyJson) {
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
            }
        });
    }

    public static class SurveyFormFragment extends FormFragment {

        public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        public static SurveyFormFragment newInstance(String surveyJson){
            SurveyFormFragment f = new SurveyFormFragment();
            Bundle bdl = new Bundle(1);
            bdl.putString("surveyJson", surveyJson);
            f.setArguments(bdl);
            return f;
        }

        public boolean submitSurvey() {
            Log.d(Constants.TAG, "Validated");
            return true;
        }

        @Override
        public void initForm(FormController controller) {

            try {
                Survey survey = OBJECT_MAPPER.readValue(getArguments().getString("surveyJson"), Survey.class);
                FormSectionController build = FormBuilder
                        .aBuilder()
                        .withContext(getContext())
                        .withSurvey(survey)
                        .build();
                controller.addSection(build);
            } catch (IOException e) {
                Log.d(Constants.TAG, "Could not read survey from json", e);
            }

        }
    }
}