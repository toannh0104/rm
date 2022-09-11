package com.crypto.recommendation.repository;

import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoHistoryRepository extends JpaRepository<CryptoHistoryEntity, Long>, JpaSpecificationExecutor {
}