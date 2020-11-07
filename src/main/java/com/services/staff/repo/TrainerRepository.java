package com.services.staff.repo;

import com.services.staff.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {
    @Query(value = "SELECT * FROM trainer WHERE sport_category >=  :category",nativeQuery = true)
    List<Trainer> getSuitableTrainers(@Param("category") int category);
}
