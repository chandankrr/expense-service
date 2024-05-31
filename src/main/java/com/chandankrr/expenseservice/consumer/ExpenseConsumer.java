package com.chandankrr.expenseservice.consumer;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.service.ExpenseService;
import com.chandankrr.expenseservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private final ExpenseService expenseService;
    private final RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseConsumer.class);

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto eventData) {
        logger.info("Received event data: {}", eventData);
        try {
            // Check for idempotency
            String key = "expense:" + eventData.getExternalId();
            if (redisService.isDuplicate(key)) {
                logger.warn("Duplicate event data: {}", eventData);
                return;
            }

            // Store the key to prevent future duplicates
            redisService.storeKey(key, Duration.ofHours(1));

            // Process the expense data
            expenseService.createExpense(eventData);
        } catch (Exception e) {
            logger.error("ExpenseConsumer: An error occurred while consuming kafka event: ", e);
        }
    }
}
