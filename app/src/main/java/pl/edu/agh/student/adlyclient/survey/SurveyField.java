package pl.edu.agh.student.adlyclient.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyField {

    private Integer fieldId;
    private String fieldName;
    private IPropertyType fieldType;

    public SurveyField() {
    }

    public SurveyField(Integer fieldId, String fieldName, IPropertyType fieldType) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public IPropertyType getFieldType() {
        return fieldType;
    }

    public void setFieldType(IPropertyType fieldType) {
        this.fieldType = fieldType;
    }
}
