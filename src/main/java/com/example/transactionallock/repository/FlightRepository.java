package com.example.transactionallock.repository;

import com.example.transactionallock.model.Flight;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Flight> findWithLockingById(Long id);
}

