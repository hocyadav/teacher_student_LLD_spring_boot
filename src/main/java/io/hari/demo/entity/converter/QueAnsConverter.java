package io.hari.demo.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hari.demo.entity.MapQuestionAns;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
public class QueAnsConverter implements AttributeConverter<MapQuestionAns, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(final MapQuestionAns mapQuestionAns) {
        return objectMapper.writeValueAsString(mapQuestionAns);
    }

    @SneakyThrows
    @Override
    public MapQuestionAns convertToEntityAttribute(final String s) {
        return objectMapper.readValue(s, MapQuestionAns.class);
    }
}
