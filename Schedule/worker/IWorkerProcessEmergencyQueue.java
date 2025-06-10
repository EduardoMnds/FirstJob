package com.job.processamento.Schedule.worker;

import org.springframework.scheduling.SchedulingException;

public interface IWorkerProcessEmergencyQueue {

    void processEmergencyQueue() throws SchedulingException;

}
