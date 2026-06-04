package com.sessac.aibackend.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sports {

    private Long id;        // 종목 id
    private String sport;   // 종목 이름
    private int num;        // 종목 별 인원 수
}
