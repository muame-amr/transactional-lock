package com.example.transactionallock.service;

import com.example.transactionallock.ExceededCapacityException;
import com.example.transactionallock.model.Flight;
import com.example.transactionallock.model.Ticket;
import com.example.transactionallock.repository.FlightRepository;
import com.example.transactionallock.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    private final TicketRepository ticketRepository;

    private void saveNewTicket(String firstName, String lastName, Flight flight) throws Exception {
        if (flight.getCapacity() <= flight.getTickets().size()) {
            throw new ExceededCapacityException();
        }
        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        flight.addTicket(ticket);
        ticketRepository.save(ticket);
    }
    @Transactional
    public void changeFlight1() throws Exception {
        Flight flight = flightRepository.findWithLockingById(1L).get();
        saveNewTicket("Robert", "Smith", flight);
        Thread.sleep(1_000);
    }

    @Transactional
    public void changeFlight2() throws Exception {
        Flight flight = flightRepository.findWithLockingById(1L).get();
        saveNewTicket("Kate", "Brown", flight);
        Thread.sleep(1_000);
    }
}
