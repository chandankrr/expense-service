package com.chandankrr.expenseservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    @JsonIgnoreProperties(value = "external_id")
    private String externalId;

    @NonNull
    @JsonIgnoreProperties(value = "amount")
    private BigDecimal amount;

    @JsonIgnoreProperties(value = "user_id")
    private String userId;

    @JsonIgnoreProperties(value = "merchant")
    private String merchant;

    @JsonIgnoreProperties(value = "currency")
    private String currency;

    @JsonIgnoreProperties(value = "created_at")
    private Timestamp createdAt;

    @JsonIgnoreProperties(value = "updated_at")
    private Timestamp updatedAt;

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
            this.updatedAt = expense.getUpdatedAt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ExpenseDto from json", e);
        }
    }

}
