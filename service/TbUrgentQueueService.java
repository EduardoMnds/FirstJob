package com.job.processamento.service;


import com.job.processamento.entity.TbUrgentQueue;


import com.job.processamento.repository.TbUrgentQueueRepository;
import com.job.processamento.utils.ConstantUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TbUrgentQueueService implements ITbUrgentQueueService {

    private final TbUrgentQueueRepository tbUrgentQueueRepository;

    @Override
    public List<TbUrgentQueue> findAllByIfProcessZero() throws Exception {
        return tbUrgentQueueRepository.findByIfProcessAndUrgentIdIsNull(0);
    }

    @Override
    public List<TbUrgentQueue> findUrgentQueueNotProcessed() throws Exception {
        return tbUrgentQueueRepository.findByInsertIdAndIfProcess(ConstantUtils.EMERGENCY_INS_PERSON_ID, 0);
    }

    @Override
    public void saveUrgentQueue(List<TbUrgentQueue> urgentQueue) throws Exception {
        if (!urgentQueue.isEmpty()) {
            tbUrgentQueueRepository.saveAll(urgentQueue);
        }
    }

    @Override
    public List<TbUrgentQueue> findTopNToUrgent(int limit) throws Exception {
        return tbUrgentQueueRepository.findTopNToUrgent(limit);
    }

    public boolean shouldProcessUrgentQueue(long totalPendingCount, long urgentCount) throws Exception {
        return totalPendingCount > ConstantUtils.MIN_TOTAL_PENDING && urgentCount <= ConstantUtils.MAX_URGENT_QUEUE;
    }

    public void markAsUrgent(List<TbUrgentQueue> oldestProcess) {
        for (TbUrgentQueue process : oldestProcess) {
            String originalInsertId = process.getInsertId();
            process.setInsertId(ConstantUtils.EMERGENCY_INS_PERSON_ID);
            process.setUrgentId(originalInsertId);
        }
    }

    @Override
    public List<TbUrgentQueue> findByIfProcessAndUrgentIdIsNotNull() throws Exception {
        return tbUrgentQueueRepository.findByIfProcessAndUrgentIdIsNotNull(0);
    }


    @Override
    public List<TbUrgentQueue> findByUrgentIdAndProcessZero(int limit) throws Exception {
        return tbUrgentQueueRepository.findByUrgentIdAndProcessZero(limit);
    }

    public void markAsProcessed(List<TbUrgentQueue> urgentProcess) throws Exception {
        for (TbUrgentQueue reference : urgentProcess) {
            reference.setIfProcess(ConstantUtils.PROCESSED_SUCCESSFULLY);
        }
    }

    @Override
    public void saveUrgentQueueProcess(List<TbUrgentQueue> saveUrgentQueueProcess) throws Exception {
        if (!saveUrgentQueueProcess.isEmpty()) {
            tbUrgentQueueRepository.saveAll(saveUrgentQueueProcess);
        }
    }
}
