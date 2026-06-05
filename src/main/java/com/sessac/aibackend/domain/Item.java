package com.sessac.aibackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="items")   // item 이라는 table로 관리할 거임.
@Getter
@Setter
@NoArgsConstructor  // 기본 빈 생성자
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // GenerationType 여러 개 중 IDENTITY 사용.
    private Long id;

    @Column(nullable = false, length = 100)  // 빈 값 허용 x. nullable := notnull 속성,,
    private String name;

    @Column(nullable = false)
    private int price;
}
