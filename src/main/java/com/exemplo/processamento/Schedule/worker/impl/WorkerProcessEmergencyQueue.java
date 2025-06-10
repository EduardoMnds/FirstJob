package com.exemplo.processamento.Schedule.worker.impl;

import com.exemplo.processamento.Schedule.worker.IWorkerProcessEmergencyQueue;

import com.exemplo.processamento.entity.TbUrgentQueue;
import com.exemplo.processamento.service.TbUrgentQueueService;
import com.exemplo.processamento.utils.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class WorkerProcessEmergencyQueue implements IWorkerProcessEmergencyQueue {

    private static final Logger log = LoggerFactory.getLogger(WorkerProcessEmergencyQueue.class);

    @Autowired
    private TbUrgentQueueService tbUrgentQueueService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void processEmergencyQueue() {

        log.info("-------------------------------- START PROCESS EMERGENCY QUEUE --------------------------------");

        try{
            List<TbUrgentQueue> urgentQueueProcess = tbUrgentQueueService.findByIfProcessAndUrgentIdIsNotNull();
            long processQueueUrgent = urgentQueueProcess.size();

            if (processQueueUrgent == 0) {
                log.info("Process emergency queue is empty");
            }

            if (processQueueUrgent > 0) {
                List<TbUrgentQueue> processLimitUrgentQueue = tbUrgentQueueService.findByUrgentIdAndProcessZero(ConstantUtils.EMERGENCY_PROCESS);
                tbUrgentQueueService.markAsProcessed(processLimitUrgentQueue);
                tbUrgentQueueService.saveUrgentQueueProcess(processLimitUrgentQueue);

                log.info("{} Deliveries sent to urgent queue", processLimitUrgentQueue.size());
            }


        }catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("-------------------------------- FINISH PROCESS EMERGENCY QUEUE --------------------------------");

    }

}
