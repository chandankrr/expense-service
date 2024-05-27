package com.chandankrr.expenseservice.controller;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.service.ExpenseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense/v1")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathVariable("userId") @NonNull String userId) {
        try {
            List<ExpenseDto> expenses = expenseService.getExpenses(userId);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDto expenseDto) {
        boolean isCreated = expenseService.createExpense(expenseDto);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create expense.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDto expenseDto) {
        boolean isUpdated = expenseService.updateExpense(expenseDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Expense updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found or update failed.");
        }
    }

}
