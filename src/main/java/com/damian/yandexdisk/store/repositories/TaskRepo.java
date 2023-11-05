package com.damian.yandexdisk.store.repositories;

import com.damian.yandexdisk.store.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
