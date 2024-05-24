package com.chandankrr.expenseservice.service;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.entity.Expense;
import com.chandankrr.expenseservice.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
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

    public boolean createExpense(ExpenseDto expenseDto) {
        setCurrency(expenseDto);
        try {
            expenseRepository.save(modelMapper.map(expenseDto, Expense.class));
            return true;
        } catch (Exception e) {
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
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    private void setCurrency(ExpenseDto expenseDto) {
        if(Objects.isNull(expenseDto.getCurrency())) {
            expenseDto.setCurrency("INR");
        }
    }
}
