package com.example.transactionallock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "flights")
@Getter
@Setter
public class Flight {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private LocalDateTime depatureTime;

    private Integer capacity;

    public Flight(String number, LocalDateTime depatureTime, Integer capacity) {
        this.number = number;
        this.depatureTime = depatureTime;
        this.capacity = capacity;
    }

    @OneToMany(mappedBy = "flight")
    private Set<Ticket> tickets;

    @Version
    private Long version;

    public void addTicket(Ticket ticket) {
        ticket.setFlight(this);
        getTickets().add(ticket);
    }
}
