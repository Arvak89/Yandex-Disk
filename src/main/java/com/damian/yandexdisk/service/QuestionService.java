package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Question;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.QuestionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionService {

    QuestionRepo questionRepo;

    public void saveQuestion(Long id, String fileName, String link) {

        questionRepo.save(Question.builder()
                        .id(id)
                .fileName(fileName)
                .link(link)
                .build());
    }

    public List<Question> fetchAll() {

        return questionRepo.findAll();
    }

    public void removeQuestion(String fileName) {

        questionRepo.deleteByFileName(fileName);
    }
}
