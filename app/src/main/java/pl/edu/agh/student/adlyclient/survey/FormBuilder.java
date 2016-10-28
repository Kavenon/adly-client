package pl.edu.agh.student.adlyclient.survey;

import android.content.Context;
import android.util.Log;

import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.LabeledFieldController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;

import pl.edu.agh.student.adlyclient.config.Constants;

public class FormBuilder {

    private Context context;
    private Survey survey;

    private FormBuilder() {
    }

    public static FormBuilder aBuilder(){
        return new FormBuilder();
    }

    public FormBuilder withContext(Context context){
        this.context = context;
        return this;
    }

    public FormBuilder withSurvey(Survey survey){
        this.survey = survey;
        return this;
    }

    public FormSectionController build(){

        assert context != null;
        assert survey != null;

        FormSectionController section = new FormSectionController(context, "Answer this questions");

        for (SurveyField surveyField : survey.getFieldList()) {
            try {
                LabeledFieldController build = build(context, surveyField);
                section.addElement(build);
            }
            catch(Exception e){
                Log.d(Constants.TAG, "Error during form building", e);
            }
        }

        return section;

    }

    private LabeledFieldController build(Context context, SurveyField x) {
        if(x.getFieldType() instanceof SimplePropertyType){
            SimplePropertyType type = (SimplePropertyType) x.getFieldType();
            if(type.getSimpleType() == PropertyType.DATE){
                return new DatePickerController(
                        context,
                        x.getFieldId().toString(),
                        x.getFieldName());
            }
            else {
                return new EditTextController(
                        context,
                        x.getFieldId().toString(),
                        x.getFieldName());
            }
        }
        else if(x.getFieldType() instanceof EnumeratedPropertyType) {
            EnumeratedPropertyType type = (EnumeratedPropertyType) x.getFieldType();
            return new SelectionController(
                    context,
                    x.getFieldId().toString(),
                    x.getFieldName(),
                    true,
                    "Select",
                    type.getPossibleValues(),
                    true);
        }
        throw new IllegalArgumentException("Field not supported");
    }
}
