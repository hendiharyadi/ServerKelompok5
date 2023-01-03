/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hendi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

    private Boolean leave_type;

    private String start_leave;

    private String end_leave;

    private String note;
    private Boolean status;

    private Integer employee_id;
    private Integer leave_day;
    /*

    private Integer manager;

    private Integer permission;

    private Timestamp date_history;*/

}
