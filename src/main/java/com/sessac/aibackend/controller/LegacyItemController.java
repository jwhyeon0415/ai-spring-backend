package com.sessac.aibackend.controller;

import com.sessac.aibackend.domain.Item;
import com.sessac.aibackend.dto.ItemRequest;
import com.sessac.aibackend.dto.ItemResponse;
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
@RequestMapping("/legacy/items")   // GetMapping x, ItemController의 최상위 경로 잡아주는,, (하위 method들의 최상위 경로,,)
public class LegacyItemController {

    private final Map<Long, Item> storage = new ConcurrentHashMap<>(); // storage: 저장소 (Map 객체)
    private final AtomicLong sequence = new AtomicLong(1);   // sequence: 카운터

    @GetMapping  // RequestMapping에 경로 지정 -> 경로 생략 가능,, ("/items") 저정 시 -> ("/legacy/items/items") 중첩됨
    public List<ItemResponse> list() {
        return storage.values().stream().map(ItemResponse::from).toList();
    }

    @GetMapping("/{id}")  // "legacy/items/{id}"로 들어오는 method는 요거 실행,,
    public ItemResponse get(@PathVariable Long id) {
        Item item = storage.get(id);

        if (item == null) {
            throw NotFoundException.of("item", id);
        }

        return ItemResponse.from(item);
    }

    @PostMapping   // legacy item이 post로 들어오면 실행됨,,
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody ItemRequest req) {
        long id = sequence.getAndIncrement();  // 1씩 증가,,
        Item saved = Item.builder().id(id).name(req.name()).price(req.price()).build();
        storage.put(id, saved);  // storage에 우리가 만든 saved된 객체 put,,  -> 배열 method에 save 마냥 흉내내기 위해 하나씩 putputput
        return ResponseEntity.created(URI.create("/legacy/items/" + id)).body(ItemResponse.from(saved));
    }

    @PutMapping("/{id}")
    public ItemResponse update(@PathVariable Long id, @Valid @RequestBody ItemRequest req) {
        Item existing = storage.get(id);

        if (existing == null) {
            throw NotFoundException.of("item", id);
        }
        existing.setName(req.name());
        existing.setPrice(req.price());
        return ItemResponse.from(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (storage.remove(id) == null) {
            throw NotFoundException.of("item", id);
        }

        return ResponseEntity.noContent().build();   //  noContent -> 404 error
    }


}
