package com.chandankrr.expenseservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.IOException;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private String externalId;

    @NonNull
    @JsonIgnoreProperties(value = "amount")
    private String amount;

    @JsonIgnoreProperties(value = "user_id")
    private String userId;

    @JsonIgnoreProperties(value = "merchant")
    private String merchant;

    @JsonIgnoreProperties(value = "currency")
    private String currency;

    @JsonIgnoreProperties(value = "created_at")
    private Timestamp createdAt;

    public ExpenseDto(String json) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            ExpenseDto expense = mapper.readValue(json, ExpenseDto.class);
            this.externalId = expense.getExternalId();
            this.amount = expense.getAmount();
            this.userId = expense.getUserId();
            this.merchant = expense.getMerchant();
            this.currency = expense.getCurrency();
            this.createdAt = expense.getCreatedAt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ExpenseDto from json", e);
        }
    }

}
