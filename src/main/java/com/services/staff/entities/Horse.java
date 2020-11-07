package com.services.staff.entities;

import com.services.staff.enums.HorsemanStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="horses")
@NoArgsConstructor
@AllArgsConstructor
public class Horse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private UUID ownerId;
    private HorsemanStatus horsemanStatus;
    private boolean isIll;
    private LocalDateTime lastTimeEat;
    private int price;

    public Horse(String name, UUID ownerId, HorsemanStatus horsemanStatus, int price){
        this.id = UUID.randomUUID();
        this.name = name;
        this.ownerId = ownerId;
        this.horsemanStatus = horsemanStatus;
        this.isIll = false;
        this.lastTimeEat = LocalDateTime.now().withNano(0);
        this.price = price;
    }

    public void eat(){
        this.lastTimeEat = LocalDateTime.now().withNano(0);
    }

    public void getRecovered(){
        this.isIll = false;
    }

};
