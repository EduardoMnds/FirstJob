package com.job.processamento.repository;


import com.job.processamento.entity.TbUrgentQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TbUrgentQueueRepository extends JpaRepository<TbUrgentQueue, BigInteger> {

    List<TbUrgentQueue> findByIfProcessAndUrgentIdIsNull(Integer ifProcess);

    List<TbUrgentQueue> findByInsertIdAndIfProcess(String insertId, Integer ifProcess);

    @Query(value = "SELECT * FROM TB_INT_CELLO_PRIME_TRACKING " +
            "WHERE IF_PROCESS = 0 AND INSERT_ID <> 'auto-emergency' " +
            "ORDER BY ID ASC LIMIT :limit", nativeQuery = true)
    List<TbUrgentQueue> findTopNToUrgent(int limit);

    List<TbUrgentQueue> findByIfProcessAndUrgentIdIsNotNull(Integer ifProcess);

    @Query(value = "SELECT * FROM TB_INT_CELLO_PRIME_TRACKING " +
            "WHERE IF_PROCESS = 0 AND INSERT_ID = 'auto-emergency' " +
            "ORDER BY ID ASC LIMIT :limit", nativeQuery = true)

    List<TbUrgentQueue> findByUrgentIdAndProcessZero(int limit);
}
