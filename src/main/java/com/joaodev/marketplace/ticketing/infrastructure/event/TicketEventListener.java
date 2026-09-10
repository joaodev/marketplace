package com.joaodev.marketplace.ticketing.infrastructure.event;

import com.joaodev.marketplace.common.infrastructure.event.dto.CustomerCreated;
import com.joaodev.marketplace.common.infrastructure.event.dto.EventUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TicketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TicketEventListener.class);

    @EventListener
    @Async
    public void handle(CustomerCreated event) {
        logger.info("CustomerCreated received {}", "");
    }

    @EventListener
    @Async
    public void handle(EventUpdated event) {
        logger.info("EventUpdated received {}", event);
    }
}
