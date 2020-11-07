package com.services.staff.entities;

import com.services.staff.enums.SportsCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public final class Trainer {
    @Id
    private UUID id;
    private String name;
    private int salary;
    private SportsCategory sportCategory;
    private int trainingPrice;

    public Trainer(String name, int salary, SportsCategory sportCategory, int trainingPrice){
        this.name = name;
        this.salary = salary;
        this.id = UUID.randomUUID();
        this.sportCategory = sportCategory;
        this.trainingPrice = trainingPrice;
    }
}
