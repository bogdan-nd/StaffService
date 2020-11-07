package com.services.staff.repo;

import com.services.staff.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VetRepository extends JpaRepository<Vet, UUID> {
}
