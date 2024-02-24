package com.cbsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbsapi.model.stmt;
@Repository
public interface stmtRepository extends JpaRepository<stmt, String> {

	List<stmt> findByAccountNumber(String accountNumber);

}
