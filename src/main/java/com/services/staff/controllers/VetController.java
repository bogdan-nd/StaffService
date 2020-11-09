package com.services.staff.controllers;


import com.services.staff.dto.VetDTO;
import com.services.staff.entities.Vet;
import com.services.staff.services.VetService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vets")
@AllArgsConstructor
public class VetController {
    private final VetService vetService;

    @GetMapping
    public ResponseEntity<List<Vet>> showVets() {
        return ResponseEntity.ok(vetService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showVetById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(vetService.getById(id));
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Vet> addVet(@RequestBody VetDTO vetDTO) {
        Vet newVet = new Vet(vetDTO.getName(), vetDTO.getSalary(), vetDTO.getConsultationPrice());

        return ResponseEntity.ok(vetService.saveVet(newVet));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable UUID id){
        vetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}