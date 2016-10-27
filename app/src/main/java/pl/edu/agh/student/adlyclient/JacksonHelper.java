package pl.edu.agh.student.adlyclient;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import pl.edu.agh.student.adlyclient.survey.Survey;

public class JacksonHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T fromMap(Map<String, String> map, Class<T> clazz){
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    public static String toString(Survey object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.e(Constants.TAG, "Could not write as string", e);
            return null;
        }
    }

    public static <T> T fromString(String json, Class<T> clazz) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Could parse json to class ", e);
            throw e;
        }
    }
}
