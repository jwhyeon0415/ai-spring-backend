package com.sessac.aibackend.service;

import com.sessac.aibackend.domain.Item;
import com.sessac.aibackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service   // 기능 없는 빈 껍데기. -> 빈으로 등록할 수 있는 순수,,
@RequiredArgsConstructor   // 생성자 주입 가능하게 만들어주는 annotation
public class ItemService {

    private final ItemRepository repository;

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
