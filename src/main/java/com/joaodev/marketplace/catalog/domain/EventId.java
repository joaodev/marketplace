package com.joaodev.marketplace.catalog.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record EventId(UUID id) {
    public EventId {
        Assert.notNull(id, "id must not be null");
    }

    public EventId() {
        this(UUID.randomUUID());
    }
}
