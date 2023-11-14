package com.damian.yandexdisk.store.repositories;

import com.damian.yandexdisk.store.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LectureRepo extends JpaRepository<Lecture, Long> {

    @Transactional
    public void deleteByFileName(String fileName);
}
