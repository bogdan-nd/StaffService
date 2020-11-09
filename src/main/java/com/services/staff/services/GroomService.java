package com.services.staff.services;

import com.services.staff.entities.Groom;
import com.services.staff.repo.GroomRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroomService {
    private final GroomRepository repository;
    @Transactional
    public List<Groom> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Groom saveGroom(Groom groom) {
        return repository.save(groom);
    }

    @Transactional
    public Groom getById(UUID id) throws NotFoundException {
        Optional<Groom> groom = repository.findById(id);
        if (groom.isPresent())
            return groom.get();
        else
            throw new NotFoundException(String.format("Groom with %s id does not exist",id));
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
