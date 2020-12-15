package com.services.staff.controllers.rest.dto;

import com.services.staff.entities.enums.SportsCategory;
import lombok.Data;

@Data
public class TrainerDTO {
    private String name;
    private int salary;
    private SportsCategory sportCategory;
    private int trainingPrice;
}
