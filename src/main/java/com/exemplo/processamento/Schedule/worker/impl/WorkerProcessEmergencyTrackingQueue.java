package com.exemplo.processamento.Schedule.worker.impl;

import com.exemplo.processamento.Schedule.worker.IWorkerProcessEmergencyTrackingQueue;
import com.exemplo.processamento.entity.TbUrgentQueue;
import com.exemplo.processamento.service.TbUrgentQueueService;
import com.exemplo.processamento.utils.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Component
public class WorkerProcessEmergencyTrackingQueue implements IWorkerProcessEmergencyTrackingQueue {

    private static final Logger log = LoggerFactory.getLogger(WorkerProcessEmergencyTrackingQueue.class);

    @Autowired
    private TbUrgentQueueService tbIntCelloPrimeTrackingService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void processEmergencyTrackingQueue() throws SchedulingException {
        log.info("-------------------------------- START EMERGENCY TRACKING QUEUE --------------------------------");

        try {
            List<TbUrgentQueue> totalProcess = tbIntCelloPrimeTrackingService.findAllByIfProcessZero();
            List<TbUrgentQueue> urgentQueue = tbIntCelloPrimeTrackingService.findUrgentQueueNotProcessed();


            long totalPendingCount = totalProcess.size();
            long urgentCount = urgentQueue.size();

            if (totalPendingCount == 0) {
                log.info("No pending records to process.");
                return;
            }

            if (!tbIntCelloPrimeTrackingService.shouldProcessUrgentQueue(totalPendingCount, urgentCount)) {
                log.info("Conditions to process urgent queue not met.");
                return;
            }

            int remainingUrgentCapacity = (int) (ConstantUtils.EMERGENCY_MAX - urgentCount);
            int fetchLimit = Math.min(ConstantUtils.EMERGENCY_MIN, remainingUrgentCapacity);

            List<TbUrgentQueue> oldestProcess = tbIntCelloPrimeTrackingService.findTopNToUrgent(fetchLimit);

            tbIntCelloPrimeTrackingService.markAsUrgent(oldestProcess);
            tbIntCelloPrimeTrackingService.saveUrgentQueue(oldestProcess);

            log.info("{} Deliveries sent to urgent queue", oldestProcess.size());

        } catch (Exception e) {
            log.error("Error processing urgent queue", e);
            throw new RuntimeException(e);
        }

        log.info("-------------------------------- END EMERGENCY TRACKING QUEUE --------------------------------");
    }
}