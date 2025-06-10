package com.job.processamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "TB_URGENT_QUEUE_TEST")
@SequenceGenerator(name = "TB_URGENT_QUEUE_TEST_SEQ", sequenceName = "TB_URGENT_QUEUE_TEST_SEQ", allocationSize = 1)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbUrgentQueue {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_URGENT_QUEUE_TEST_SEQ")
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "PROCESS_TYPE", length = 50, nullable = false)
    private String processType;

    @Column(name = "IF_PROCESS")
    private Integer ifProcess;

    @Column(name = "IF_MSG", length = 600, nullable = true)
    private String ifMsg;

    @Column(name = "TRACKING_CODE", length = 100, nullable = true)
    private String trackingCode;

    @Column(name = "ORDER_NUMBER", length = 100, nullable = true)
    private String orderNumber;

    @Column(name = "REMARKS", length = 100, nullable = true)
    private String remarks;

    @Column(name = "TRACKING_DATETIME")
    private Date trackingDateTime;

    @Column(name = "INSERT_ID", length = 100, nullable = true)
    private String insertId;

    @Column(name = "INSERT_DATE")
    private Date insertDate;

    @Column(name = "UPDATE_ID", length = 100, nullable = true)
    private String updateId;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "URGENT_ID", length = 100, nullable = true)
    private String urgentId;

}