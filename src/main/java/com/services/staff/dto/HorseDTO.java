package com.services.staff.dto;

import com.services.staff.enums.HorsemanStatus;
import lombok.Data;


import java.util.UUID;

@Data
public class HorseDTO {
    private final String name;
    private final UUID ownerId;
    private final HorsemanStatus horsemanStatus;
    private final int price;

}
