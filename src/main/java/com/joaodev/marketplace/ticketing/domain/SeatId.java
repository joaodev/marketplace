package com.joaodev.marketplace.ticketing.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record SeatId(String id) {
    public SeatId {
        Assert.notNull(id, "id must not be null");
    }
}
