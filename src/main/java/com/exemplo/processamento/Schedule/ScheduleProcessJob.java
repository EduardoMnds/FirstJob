package com.exemplo.processamento.Schedule;

import com.exemplo.processamento.Schedule.worker.IWorkerProcessEmergencyQueue;
import com.exemplo.processamento.Schedule.worker.IWorkerProcessEmergencyTrackingQueue;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableAsync
@Component
public class ScheduleProcessJob {

    @Autowired
    private IWorkerProcessEmergencyTrackingQueue workerProcessEmergencyTrackingQueue;

    @Autowired
    private IWorkerProcessEmergencyQueue workerProcessEmergencyQueue;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleProcessJob.class);

    private boolean processEmergencyTrackingQueue 			= false;
    private boolean processEmergencyQueue 					= false;

    @Scheduled(cron = "0 */2 * * * *")
    public void processEmergencyTracking() throws SchedulingException {
        if (!this.processEmergencyTrackingQueue){
            long time = System.currentTimeMillis();
            try {
                logger.info("Process Emergency Tracking Job {}", new Date().toString());
                this.workerProcessEmergencyTrackingQueue.processEmergencyTrackingQueue();
            }catch (ServiceException e) {
                logger.error("An error occured during processEmergencyTracking. ServiceException" + e);
            }

            logger.info("Job processEmergencyTracking finished. Duration {} seconds.", ((System.currentTimeMillis() - time) / 1000));
        }
    }

    @Scheduled(cron = "0 */1 * * * *") // Processar o que está na fila de urgent
    public void processEmergencyQueue() throws SchedulingException {
        if (!this.processEmergencyQueue){
            long time = System.currentTimeMillis();
            try {
                logger.info("Process Emergency Tracking Job {}", new Date().toString());
                this.workerProcessEmergencyQueue.processEmergencyQueue();
            }catch (ServiceException e) {
                logger.error("An error occured during processEmergencyTracking. ServiceException" + e);
            }

            logger.info("Job processEmergencyTracking finished. Duration {} seconds.", ((System.currentTimeMillis() - time) / 1000));
        }
    }

}
