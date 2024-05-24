package com.chandankrr.expenseservice.consumer;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private final ExpenseService expenseService;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseConsumer.class);

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto eventData) {
        logger.info("Received event data: {}", eventData);
        try {
            expenseService.createExpense(eventData);
        } catch (Exception e) {
            logger.error("ExpenseConsumer: An error occurred while consuming kafka event: ", e);
        }
    }
}
