package com.chandankrr.expenseservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    private String userId;

    private BigDecimal amount;

    private String merchant;

    private String currency;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    private void onCreate() {
        if (externalId == null) {
            externalId = UUID.randomUUID().toString();
        }
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
