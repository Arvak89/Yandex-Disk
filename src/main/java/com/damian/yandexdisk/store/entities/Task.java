package com.damian.yandexdisk.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task{

    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "link", length = 700, nullable = false)
    String link;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    List<Material> materials;

    @Column
    String deadline;
}