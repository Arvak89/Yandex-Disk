package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Task;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.TaskRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskService {

    TaskRepo taskRepo;

    public void saveTask(Long id, String fileName, String deadline, String link) {

        taskRepo.save(Task.builder()
                .id(id)
                .fileName(fileName)
                .deadline(deadline)
                .link(link)
                .build());
    }

    public List<Task> fetchAll() {

        return taskRepo.findAll();
    }

    public void removeTask(String fileName) {

        taskRepo.deleteByFileName(fileName);
    }
}