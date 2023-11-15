package com.example.batch10.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class FileSaveTest {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String fileName;

    @Column
    private String filePath;

    @Column
    private String name; // "name" 필드 추가

    @Column
    private String age; // "age" 필드 추가

    @Column
    private String email; // "email" 필드 추가

}

