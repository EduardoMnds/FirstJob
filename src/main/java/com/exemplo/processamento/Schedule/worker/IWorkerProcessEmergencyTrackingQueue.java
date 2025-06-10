package com.exemplo.processamento.Schedule.worker;

import org.springframework.scheduling.SchedulingException;

public interface IWorkerProcessEmergencyTrackingQueue {

    public void processEmergencyTrackingQueue() throws SchedulingException;

}