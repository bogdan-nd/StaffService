package com.services.staff.dto;

import com.services.staff.enums.SportsCategory;
import lombok.Data;

@Data
public class TrainerDTO {
    private String name;
    private int salary;
    private SportsCategory sportCategory;
    private int trainingPrice;
}
