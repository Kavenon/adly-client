package pl.edu.agh.student.adlyclient.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePropertyType implements IPropertyType, Serializable {

    private PropertyType simpleType;

    public SimplePropertyType() {
    }

    public SimplePropertyType(PropertyType simpleType) {
        this.simpleType = simpleType;
    }

    public PropertyType getSimpleType() {
        return simpleType;
    }

    public void setSimpleType(PropertyType simpleType) {
        this.simpleType = simpleType;
    }
}
