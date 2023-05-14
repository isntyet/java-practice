package com.isntyet.java.practice.human.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.LinkedHashSet;
import java.util.Objects;

@Slf4j
public class HumanTagConverter implements AttributeConverter<LinkedHashSet<String>, String> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LinkedHashSet<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("HumanTagConverter convertToDatabaseColumn fail = {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public LinkedHashSet<String> convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return new LinkedHashSet<>();
        }

        try {
            return OBJECT_MAPPER.readValue(dbData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("HumanTagConverter convertToEntityAttribute fail = {}", e.getMessage(), e);
        }
        return new LinkedHashSet<>();
    }
}
