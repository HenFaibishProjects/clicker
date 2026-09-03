package com.plusOne.clicker.bigquery;

import com.plusOne.clicker.domain.AdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BigQueryInMemoryRepository implements BigQueryRepository {

    private final List<AdEvent> events =
            new CopyOnWriteArrayList<>();

    private static final Logger log =
            LoggerFactory.getLogger(BigQueryInMemoryRepository.class);

    @Override
    public void save(AdEvent event) {
        events.add(event);
        log.info(
                "[component=BigQueryInMemoryRepository][action=save] eventId={} type={}",
                event.eventId(),
                event.type()
        );
    }

    @Override
    public List<AdEvent> findAll() {
        return List.copyOf(events);
    }
}
