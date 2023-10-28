package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LectureService {

    LectureRepo lectureRepo;

    public void saveLecture(int year, String fileName, byte[] file){

        lectureRepo.save(Lecture.builder()
                .year(year)
                .fileName(fileName)
                .file(file)
                .build());
    }
}
