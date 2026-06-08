package com.sessac.aibackend.controller;

import com.sessac.aibackend.domain.Item;
import com.sessac.aibackend.dto.ItemRequest;
import com.sessac.aibackend.dto.ItemResponse;
import com.sessac.aibackend.error.NotFoundException;
import com.sessac.aibackend.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;   // itemService 의존성 주입,,

    @GetMapping
    public List<ItemResponse> list() {
        return itemService.findAll().stream().map(ItemResponse::from).toList();
    }
    // findall() 결과 list로 빼

    @GetMapping("/{id}")
    public ItemResponse get(@PathVariable Long id) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> NotFoundException.of("item", id));    // orElseThrow(): 만약에 없다면 (findBy(id)가 Optional이니까)
        return ItemResponse.from(item);
    }

    @PostMapping       // Update는 post랑 다르게 id가 path에 들어옴,,,
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody ItemRequest req) {   // DTO에 담아서
        Item saved = itemService.save(req.toEntity());  // toEntity(): DTO로 미리 생성해 놓음. 우리가 만든 모양이랑 다르니까
        URI location = URI.create("/items/" + saved.getId());  // Proxy 객체. 가짜 객체 만들어 놓고,,
        return ResponseEntity.created(location).body(ItemResponse.from(saved));  // Body 에 우리가 save한 객체,,
    }

    @PutMapping("/{id}")
    public ItemResponse update(@PathVariable Long id, @Valid @RequestBody ItemRequest req) {
        Item item = itemService.findById(id)    // 얘는 영속된 상태로 오겠구나...
                .orElseThrow(() -> NotFoundException.of("item", id));
        item.setName(req.name());
        item.setPrice(req.price());
        return ItemResponse.from(itemService.save(item));  // 1차 값이랑 달라(스냅샷 찍어놓은 거랑 달라) -> 인서트 x, update 켜서 ,
    }

    @DeleteMapping("/{id}")  // 삭제는 두가지. (영속성..)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itemService.existsById(id)) {
            throw NotFoundException.of("item", id);
        }
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

// Service 비즈니스 로직 레이어 제공.
// 최종적으로 컨트롤러에서 서비스 주입 받아서.
// (모든 건 영속된 객체 기준으로..)
