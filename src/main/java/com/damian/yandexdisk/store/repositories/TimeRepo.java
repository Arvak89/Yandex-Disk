package com.damian.yandexdisk.store.repositories;

import com.damian.yandexdisk.store.entities.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepo extends JpaRepository<Time, Long> {
}
