package pl.edu.agh.student.adlyclient;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormFragment;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;

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

        MyFormFragment formFragment = handleRetainedFragment();
        setSubmitAction(formFragment);

    }

    @NonNull
    private MyFormFragment handleRetainedFragment() {
        MyFormFragment formFragment;Fragment retainedFragment = getSupportFragmentManager().findFragmentByTag(FORM_FRAGMENT_KEY);
        if (retainedFragment != null && retainedFragment instanceof MyFormFragment) {
            formFragment = (MyFormFragment) retainedFragment;
        } else {
            formFragment = new MyFormFragment();
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, formFragment, FORM_FRAGMENT_KEY)
                .commit();
        }
        return formFragment;
    }

    private void setSubmitAction(final MyFormFragment formFragment) {
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFragment.submitSurvey();
            }
        });
    }

    public static class MyFormFragment extends FormFragment {

        public boolean submitSurvey() {
            Log.d(Constants.TAG, "Validated");
            return true;
        }

        @Override
        public void initForm(FormController controller) {
          /*
            @todo: get object from extras (which was previously downloaded from adly endpoint)
         */
            // ========================================
            List<SurveyField> fields = new ArrayList<>();
            fields.add(new SurveyField(1,"name", new SimplePropertyType(PropertyType.TEXT)));
            fields.add(new SurveyField(2,"gender", new EnumeratedPropertyType(GenderPropertyEnum.stringNames())));

            Survey survey = new Survey();
            survey.setSurveyId(1);
            survey.setFieldList(fields);
            // ========================================

            FormSectionController build = FormBuilder
                    .aBuilder()
                    .withContext(getContext())
                    .withSurvey(survey)
                    .build();

            controller.addSection(build);
        }
    }
}