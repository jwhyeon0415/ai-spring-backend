package com.sessac.aibackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="departments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            unique = true,
            nullable = false,
            length = 100
    )
    private String deptname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeptType depttype;


}
