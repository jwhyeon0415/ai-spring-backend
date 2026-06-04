package com.sessac.aibackend.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor  // 기본 빈 생성자
@AllArgsConstructor
@Builder
public class Item {

    private Long id;
    private String name;
    private int price;
}
