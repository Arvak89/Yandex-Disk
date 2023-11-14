package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Material;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.MaterialRepo;
import com.damian.yandexdisk.store.repositories.TaskRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MaterialService {

    MaterialRepo materialRepo;
    TaskRepo taskRepo;

    public void saveMaterial(String name, String link, Long taskId) {

        materialRepo.save(Material.builder()
                .fileName(name)
                .link(link)
                .task(taskRepo.findById(taskId).orElseThrow())
                .build());
    }

    public List<Material> fetchAll() {

        return materialRepo.findAll();
    }

    public void removeMaterial(String fileName) {

        materialRepo.deleteByFileName(fileName);
    }
}