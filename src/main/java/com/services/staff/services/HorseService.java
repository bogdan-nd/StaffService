package com.services.staff.services;


import com.services.staff.entities.Horse;
import com.services.staff.enums.HorsemanStatus;
import com.services.staff.repo.HorseRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HorseService {
    private final HorseRepository repository;

    @Transactional
    public List<Horse> findAll() {
        return repository.findAll();
    }

    @Transactional
    public List<Horse> findSuitable(HorsemanStatus status) {
        return repository.getSuitableHorses(status.ordinal());
    }

    @Transactional
    public Horse saveHorse(Horse horse) {
        return repository.save(horse);
    }

    @Transactional
    public Horse getById(UUID id) throws NotFoundException {
        Optional<Horse> horse =  repository.findById(id);
        if (horse.isPresent())
            return horse.get();
        else
            throw new NotFoundException(String.format("Horse with %s id doesn`t exist",id));
    }

    @Transactional
    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    @Transactional
    public List<Horse> getIllHorses() {
        return repository.getIllHorses();
    }

}
