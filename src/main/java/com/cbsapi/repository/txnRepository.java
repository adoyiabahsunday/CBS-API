package com.cbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbsapi.model.txn;
@Repository
public interface txnRepository extends JpaRepository<txn, Integer> {

}
