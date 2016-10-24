package pl.edu.agh.student.adlyclient.survey;

public class SurveyFieldResponse {

    private Integer id;
    private String value;

    public SurveyFieldResponse() {
    }

    public SurveyFieldResponse(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
