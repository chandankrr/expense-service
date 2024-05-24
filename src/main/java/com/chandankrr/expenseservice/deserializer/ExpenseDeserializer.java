package com.chandankrr.expenseservice.deserializer;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class ExpenseDeserializer implements Deserializer<ExpenseDto> {

    private final ObjectMapper objectMapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public ExpenseDto deserialize(String topic, byte[] data) {
        try{
            return objectMapper.readValue(data, ExpenseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ExpenseDto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}
}
