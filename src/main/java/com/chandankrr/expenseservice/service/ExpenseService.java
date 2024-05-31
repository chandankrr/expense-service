package com.chandankrr.expenseservice.service;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.entity.Expense;
import com.chandankrr.expenseservice.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);


    public boolean createExpense(ExpenseDto expenseDto) {
        setCurrency(expenseDto);
        try {
            expenseRepository.save(dtoToExpense(expenseDto));
            logger.info("Expense created");
            return true;
        } catch (Exception e) {
            logger.info("Error creating expense");
            return false;
        }
    }

    public boolean updateExpense(ExpenseDto expenseDto) {
        Optional<Expense> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),
                expenseDto.getExternalId());

        if (expenseFoundOpt.isEmpty()) {
            return false;
        }

        Expense expense = expenseFoundOpt.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency()) ? expenseDto.getCurrency() : expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant()) ? expenseDto.getMerchant() : expense.getMerchant());
        expense.setAmount(expenseDto.getAmount());

        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDto> getExpenses(String userId) {
        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        return expenseList.stream()
                .map(this::expenseToDto)
                .collect(Collectors.toList());
    }

    private void setCurrency(ExpenseDto expenseDto) {
        if (Objects.isNull(expenseDto.getCurrency())) {
            expenseDto.setCurrency("INR");
        }
    }

    private Expense dtoToExpense(ExpenseDto expenseDto) {
        return Expense.builder()
                .userId(expenseDto.getUserId())
                .externalId(expenseDto.getExternalId())
                .amount(expenseDto.getAmount())
                .merchant(expenseDto.getMerchant())
                .currency(expenseDto.getCurrency())
                .createdAt(expenseDto.getCreatedAt())
                .updatedAt(expenseDto.getUpdatedAt())
                .build();
    }

    private ExpenseDto expenseToDto(Expense expense) {
        return ExpenseDto.builder()
                .externalId(expense.getExternalId())
                .userId(expense.getUserId())
                .amount(expense.getAmount())
                .merchant(expense.getMerchant())
                .currency(expense.getCurrency())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }

}
