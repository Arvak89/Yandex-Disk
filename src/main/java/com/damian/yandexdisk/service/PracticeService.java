package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Practice;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.PracticeRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PracticeService {

    PracticeRepo practiceRepo;

    public void savePractice(Long id, int year, String fileName, String link){

        practiceRepo.save(Practice.builder()
                        .id(id)
                .year(year)
                .fileName(fileName)
                .link(link)
                .build());
    }

    public List<Practice> fetchAll(){

        return practiceRepo.findAll();
    }

    public void removePractice(String fileName){

        practiceRepo.deleteByFileName(fileName);
    }
}