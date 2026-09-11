package com.joaodev.marketplace.ticketing.domain;

public class SeatAlreadyReservedException extends RuntimeException {
    public SeatAlreadyReservedException(SeatId seatId) {
        super("Seat with id " + seatId + " is already reserved");
    }
}
