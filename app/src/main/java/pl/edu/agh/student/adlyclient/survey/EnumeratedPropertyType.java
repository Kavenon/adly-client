package pl.edu.agh.student.adlyclient.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnumeratedPropertyType implements IPropertyType, Serializable {

    private List<String> possibleValues;

    public EnumeratedPropertyType() {
    }

    public EnumeratedPropertyType(List<String> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public List<String> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(List<String> possibleValues) {
        this.possibleValues = possibleValues;
    }

}
