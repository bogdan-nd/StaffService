package com.services.staff.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Groom {
    @Id
    private UUID id;
    private String name;
    private int salary;
    private int carePrice;
    private int horseFedNumber;

    public Groom(String name, int salary, int carePrice){
        this.name = name;
        this.salary = salary;
        this.id = UUID.randomUUID();
        this.carePrice = carePrice;
        this.horseFedNumber = 0;
    }

    public void giveFood(){
        horseFedNumber += 1;
    }

}
