package pl.edu.agh.student.adlyclient.survey;

import java.util.List;

public class SurveyResponse {

    private Integer surveyId;
    private String uuid;
    private List<SurveyFieldResponse> fieldList;

    public SurveyResponse(Integer surveyId, String uuid, List<SurveyFieldResponse> fieldList) {
        this.surveyId = surveyId;
        this.uuid = uuid;
        this.fieldList = fieldList;
    }

    public SurveyResponse() {
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<SurveyFieldResponse> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<SurveyFieldResponse> fieldList) {
        this.fieldList = fieldList;
    }
}
