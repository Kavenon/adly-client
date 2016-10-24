package pl.edu.agh.student.adlyclient.survey.type;

import java.util.ArrayList;
import java.util.List;

public enum GenderPropertyEnum {
    MALE,
    FEMALE;

    public static List<String> stringNames(){
        List<String> names = new ArrayList<>();
        for (GenderPropertyEnum genderPropertyEnum : GenderPropertyEnum.values()) {
            names.add(genderPropertyEnum.name());
        }
        return names;
    }
}
