package com.company.mapper;

import com.company.enums.TransactionsStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfoMapper {

    String t_id;
    Long t_amount;
    LocalDateTime t_created_date;
    TransactionsStatus t_status;

    String cf_id;
    String cf_number;

    String clf_id;
    String clf_name;
    String clf_surname;
    String clf_phone;


    String ct_id;
    String ct_number;

    String clt_id;
    String clt_name;
    String clt_surname;
    String clt_phone;

}
