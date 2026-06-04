package com.sessac.aibackend.controller;

import com.sessac.aibackend.domain.Sports;
import com.sessac.aibackend.dto.SportsRequest;
import com.sessac.aibackend.dto.SportsResponse;
import com.sessac.aibackend.error.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/legacy/sports")
public class SportsController {

    private final Map<Long, Sports> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @GetMapping
    public List<SportsResponse> list() {
        return storage.values().stream().map(SportsResponse::from).toList();
    }

    @GetMapping("/{id}")
    public SportsResponse get(@PathVariable Long id) {
        Sports sports = storage.get(id);
        if (sports == null) {
            throw NotFoundException.of("sports", id);
        }
        return SportsResponse.from(sports);
    }

    @PostMapping
    public ResponseEntity<SportsResponse> create(@Valid @RequestBody SportsRequest req) {
        long id = sequence.getAndIncrement();
        Sports saved = Sports.builder().id(id).sport(req.sport()).num(req.num()).build();
        storage.put(id, saved);
        return ResponseEntity.created(URI.create("/legacy/sports" + id)).body(SportsResponse.from(saved));
    }

    @PutMapping("/{id}")
    public SportsResponse update(@PathVariable Long id, @Valid @RequestBody SportsRequest req) {
        Sports existing = storage.get(id);
        if (existing == null) {
            throw NotFoundException.of("sports", id);
        }
        existing.setSport(req.sport());
        existing.setNum(req.num());
        return SportsResponse.from(existing);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (storage.remove(id) == null) {
            throw NotFoundException.of("sports", id);
        }
        return ResponseEntity.noContent().build();
    }
}
