package com.chandankrr.expenseservice.repository;

import com.chandankrr.expenseservice.entity.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List<Expense> findByUserId(String userId);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);
}
