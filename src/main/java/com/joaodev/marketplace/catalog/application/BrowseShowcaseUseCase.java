package com.joaodev.marketplace.catalog.application;

import com.joaodev.marketplace.catalog.application.dto.EventOutput;
import com.joaodev.marketplace.catalog.domain.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BrowseShowcaseUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BrowseShowcaseUseCase.class);

    private final EventRepository eventRepository;
    private final EventEnricher eventEnricher;

    public BrowseShowcaseUseCase(EventRepository eventRepository, EventEnricher eventEnricher) {
        this.eventRepository = eventRepository;
        this.eventEnricher = eventEnricher;
    }

    public List<EventOutput> execute() {
        var features =  eventRepository.findAll().stream().map(eventEnricher::enrich).toList();

        var events = features.stream()
                .map(CompletableFuture::join)
                .map(EventOutput::from)
                .toList();

        logger.info("{} events enriched", events.size());

        return events;
    }
}
