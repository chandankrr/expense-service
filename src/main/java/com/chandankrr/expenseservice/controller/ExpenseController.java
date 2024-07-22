package com.chandankrr.expenseservice.controller;

import com.chandankrr.expenseservice.dto.ExpenseDto;
import com.chandankrr.expenseservice.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get expenses by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseDto.class))),
            @ApiResponse(responseCode = "404", description = "Expenses not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathVariable("userId") @NonNull String userId) {
        try {
            List<ExpenseDto> expenses = expenseService.getExpenses(userId);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Failed to create expense",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDto expenseDto) {
        boolean isCreated = expenseService.createExpense(expenseDto);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create expense.");
        }
    }

    @Operation(summary = "Update an existing expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Expense not found or update failed",
                    content = @Content(mediaType = "application/json"))
    })
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
