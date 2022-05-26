package com.company.mapper;

import com.company.enums.TransactionsStatus;

import java.time.LocalDateTime;

public interface TransactionsInfoMapper {

    String getT_id();
    Long getT_amount();
    LocalDateTime getT_created_date();
    TransactionsStatus getT_status();

    String getCf_id();
    String getCf_number();

    String getClf_id();
    String getClf_name();
    String getClf_surname();
    String getClf_phone();


    String getCt_id();
    String getCt_number();

    String getClt_id();
    String getClt_name();
    String getClt_surname();
    String getClt_phone();

}
