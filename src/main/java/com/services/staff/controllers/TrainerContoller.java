package com.services.staff.controllers;


import com.services.staff.dto.TrainerDTO;
import com.services.staff.entities.Trainer;
import com.services.staff.enums.SportsCategory;
import com.services.staff.services.TrainerService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("trainers")
@AllArgsConstructor
public class TrainerContoller {
    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<Trainer>> showTrainers() {
        return ResponseEntity.ok(trainerService.getAll());
    }

    @GetMapping("client-category/{category}")
    public ResponseEntity<List<Trainer>> getSuitableTrainer(@PathVariable SportsCategory category){
        return ResponseEntity.ok(trainerService.getSuitable(category));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showTrainerById(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(trainerService.getById(id));
        }
        catch (NotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<Trainer> addTrainer(@RequestBody TrainerDTO trainerDTO) {
        Trainer newTrainer = new Trainer(trainerDTO.getName(), trainerDTO.getSalary(),
               trainerDTO.getSportCategory(), trainerDTO.getTrainingPrice());

        return ResponseEntity.ok(trainerService.saveTrainer(newTrainer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable UUID id){
        trainerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
