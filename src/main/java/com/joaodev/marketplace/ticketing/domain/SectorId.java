package com.joaodev.marketplace.ticketing.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record SectorId(String id) {
    public SectorId {
        Assert.notNull(id, "id must not be null");
    }
}
