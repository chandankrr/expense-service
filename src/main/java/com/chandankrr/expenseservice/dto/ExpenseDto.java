package com.chandankrr.expenseservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

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

}
