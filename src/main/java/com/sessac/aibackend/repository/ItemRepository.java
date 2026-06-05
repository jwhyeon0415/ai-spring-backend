package com.sessac.aibackend.repository;

import com.sessac.aibackend.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스 구현체 있어야 o, JPA가 알아서 관리 -> Annotation 필요 x
public interface ItemRepository extends JpaRepository<Item, Long> {
}
