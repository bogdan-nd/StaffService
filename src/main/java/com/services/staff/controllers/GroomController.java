package com.services.staff.controllers;

import com.services.staff.dto.GroomDTO;
import com.services.staff.entities.Groom;
import com.services.staff.services.GroomService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("grooms")
@AllArgsConstructor
public class GroomController {
    private final GroomService groomService;

    @GetMapping
    public ResponseEntity<List<Groom>> showGrooms() {
        return ResponseEntity.ok(groomService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showGroomById(@PathVariable UUID id){
        try{
        return ResponseEntity.ok(groomService.getById(id));
        }
        catch (NotFoundException exception) {
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Groom> addGroom(@RequestBody GroomDTO groomDTO) {
        Groom newGroom = new Groom(groomDTO.getName(), groomDTO.getSalary(),
                groomDTO.getCarePrice());

        return ResponseEntity.ok(groomService.saveGroom(newGroom));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGroom(@PathVariable UUID id){
        groomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
