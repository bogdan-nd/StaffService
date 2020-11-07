package com.services.staff.services;


import com.services.staff.entities.Trainer;
import com.services.staff.enums.SportsCategory;
import com.services.staff.repo.TrainerRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainerService {
    private final TrainerRepository repository;

    @Transactional
    public List<Trainer> getAll() {
        return repository.findAll();
    }

    @Transactional
    public List<Trainer> getSuitable(SportsCategory category) {
        return repository.getSuitableTrainers(category.ordinal());
    }

    @Transactional
    public Trainer saveTrainer(Trainer trainer) {
        return repository.save(trainer);
    }

    @Transactional
    public Trainer getById(UUID id) throws NotFoundException {
        Optional<Trainer> trainer = repository.findById(id);
        if(trainer.isPresent())
            return trainer.get();
        else
            throw new NotFoundException(String.format("Trainer with %s id doesn`t exist",id));
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
