package com.damian.yandexdisk.store.repositories;

import com.damian.yandexdisk.store.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepo extends JpaRepository<Lecture, Long> {

    Lecture deleteById(long id);
}
