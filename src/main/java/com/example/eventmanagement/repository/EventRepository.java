package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository {

    private final ConcurrentHashMap<Long, Event> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(sequence.getAndIncrement());
        }
        store.put(event.getId(), event);
        return event;
    }

    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>(store.values());
        events.sort(Comparator.comparing(Event::getStartTime));
        return events;
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
