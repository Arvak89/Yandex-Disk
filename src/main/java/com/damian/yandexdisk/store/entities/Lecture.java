package com.damian.yandexdisk.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Lecture {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Lob
    @Column(name = "file", columnDefinition="BLOB")
    byte[] file;

    @Column(name = "file_name")
    String fileName;

    int year;
}