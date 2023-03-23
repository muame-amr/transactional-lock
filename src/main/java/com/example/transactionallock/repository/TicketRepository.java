package com.example.transactionallock.repository;

import com.example.transactionallock.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> { }
