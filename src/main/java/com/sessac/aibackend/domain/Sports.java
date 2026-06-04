package com.sessac.aibackend.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sports {

    private Long id;
    private String sport;
    private int num;   // 경기 인원 수
}
