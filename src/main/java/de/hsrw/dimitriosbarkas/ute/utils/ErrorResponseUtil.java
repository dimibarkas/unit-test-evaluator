package de.hsrw.dimitriosbarkas.ute.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseUtil {


    public static ResponseEntity<Map<String, Object>> buildTechnicalServerError(String errorDetail) {
        return ErrorResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, "Es ist ein technischer Fehler aufgetreten: " + errorDetail);
    }

    /**
     * Creates response entity with additional fields.
     *
     * @param status http status
     * @param errorText error text
     * @return response entity
     */
    public static ResponseEntity<Map<String, Object>> build(HttpStatus status, String errorText) {
        Map<String, Object> result = new HashMap<>();
        result.put("errorText", errorText);
        return new ResponseEntity<>(result, status);
    }
}
