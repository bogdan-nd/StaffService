package com.services.staff.services;

import com.services.staff.repo.VetRepository;
import com.services.staff.entities.Vet;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VetService{
    private final VetRepository repository;

    @Transactional
    public List<Vet> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Vet saveVet(Vet vet) {
        return repository.save(vet);
    }

    @Transactional
    public Vet getById(UUID id) throws NotFoundException {
        Optional<Vet> vet = repository.findById(id);
        if(vet.isPresent())
            return vet.get();
        else
            throw new NotFoundException(String.format("Vet with %s id doesn`t exist",id));
    }

    @Transactional
    public void deleteById(UUID id){
        repository.deleteById(id);
    }
}
