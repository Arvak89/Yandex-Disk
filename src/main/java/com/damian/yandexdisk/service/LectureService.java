package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LectureService {

    LectureRepo lectureRepo;

    public void saveLecture(int year, String fileName, String link){

        lectureRepo.save(Lecture.builder()
                .year(year)
                .fileName(fileName)
                .link(link)
                .build());
    }

    public List<Lecture> fetchAll(){

        return lectureRepo.findAll();
    }

    public Lecture removeLecture(long lectureId){

        return lectureRepo.deleteById(lectureId);
    }
}
