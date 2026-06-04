package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ItemRequest(
        @NotBlank String name,  // 빈 공백 허용  x
        @Min(0) int price             // 둘 다 제약조건 1개인 column,,
) {
    public Item toEntity() {
        return Item.builder().name(name).price(price).build();  // Item 객체 하나 생성. (name=name, price=price, build x)
    }
}
