package com.job.processamento.service;

import com.job.processamento.entity.TbUrgentQueue;

import java.util.List;

public interface ITbUrgentQueueService {

    List<TbUrgentQueue> findAllByIfProcessZero() throws Exception;

    List<TbUrgentQueue> findUrgentQueueNotProcessed() throws Exception;

    void saveUrgentQueue(List<TbUrgentQueue> urgentQueue) throws Exception;

    List<TbUrgentQueue> findTopNToUrgent(int limit) throws Exception;

    List<TbUrgentQueue> findByIfProcessAndUrgentIdIsNotNull() throws Exception;

    List<TbUrgentQueue> findByUrgentIdAndProcessZero(int limit) throws Exception;

    void saveUrgentQueueProcess(List<TbUrgentQueue> urgentQueue) throws Exception;
}
