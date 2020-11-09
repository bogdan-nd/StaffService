package com.services.staff.repo;

import com.services.staff.entities.Groom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroomRepository extends JpaRepository<Groom, UUID> {
}
