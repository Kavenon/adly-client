package pl.edu.agh.student.adlyclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import pl.edu.agh.student.adlyclient.survey.Survey;

public class FieldListJacksonTest {

    @Test
    public void test() throws IOException {

        InputStream resourceAsStream = FieldListJacksonTest.class.getClassLoader().getResourceAsStream("fields.json");
        String json = IOUtils.toString(resourceAsStream);

        ObjectMapper om = new ObjectMapper();
        Survey survey = om.readValue(json, Survey.class);

        System.out.println(survey);

    }

}
