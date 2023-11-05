package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Time;
import com.damian.yandexdisk.store.repositories.TimeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.naming.SelectorContext.prefix;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class Disk {

    @Value(value = "${Authorization}")
    @NonFinal
    String authorization;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    RestTemplate restTemplate = new RestTemplate();

    LectureService lectureService;
    MaterialService materialService;
    PracticeService practiceService;
    TaskService taskService;
    QuestionService questionService;

    TimeRepo timeRepo;
    ObjectMapper objectMapper;

    public void checkNewFiles() {

        List<Time> time = timeRepo.findAll();

        JsonNode json = connectDisk("").get("_embedded").get("items");

        if (!json.isEmpty()) {

            json.forEach((e) -> {

                if (e.get("type").asText().equals("dir")) {

                    String dir = e.get("name").asText();
                    String url = "/" + e.get("name").toString().replaceAll("\"", "");

                    JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                    for (JsonNode el : jsonSecond) {

                        Date timeOfFile;
                        String timeString = el.get("created").asText();

                        try {
                            timeOfFile = dateFormat.parse(timeString.substring(0, timeString.length() - 6));
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

//                        if (time.get(0).getTime().before(timeOfFile)) {
                        if (true) {
                            switch (dir) {
                                case "Practices": {
                                    String name = el.get("name").asText();
                                    practiceService.savePractice(
                                            Integer.parseInt(name.substring(name.length() - 6)),
                                            name,
                                            el.get("file").asText()
                                    );
                                    break;
                                }
                                case "Lectures": {
                                    lectureService.saveLecture(
                                            2134,
                                            "asdasdas",
                                            el.get("file").asText()
                                    );
                                    break;
                                }
                                case "Materials": {
                                    materialService.saveMaterial(
                                            el.get("name").asText(),
                                            el.get("file").asText()
                                    );
                                    System.out.println(el.get("file").asText());
                                    break;
                                }
                                case "Tasks": {
                                    taskService.saveTask(
                                            2103,
                                            "type",
                                            "name",
                                            new Date()
                                    );
                                    break;
                                }
                                case "Questions": {
                                    String name = el.get("name").asText();
                                    questionService.saveQuestion(
                                            Integer.parseInt(name.substring(name.length() - 6)),
                                            "question",
                                            "answer"
                                    );
                                    break;
                                }
                            }

                        }
                    }
                }
            });
        }
    }

//        timeRepo.save(Time.builder()
//                .time(new Date())
//                .id(1L)
//                .build());
//    }
//
//    public void removeOldFiles() {
//        JsonNode json = connectDisk("").get("_embedded").get("items");
//
//        if (!json.isEmpty()) {
//
//            json.forEach((e) -> {
//
////                if (e.get("type").asText().equals("dir")) {
//                if (true) {
//                    String dir = e.get("name").asText();
//
//                    switch (dir) {
//                        case "Lectures": {
//
//                            List<String> lectures = lectureService.fetchAll().stream().map(lecture -> lecture.getFileName()).collect(Collectors.toList());
//                            System.out.println(lectures);
//                            String url = "/" + e.get("name").toString().replaceAll("\"", "");
//                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");
//
//                            for (JsonNode el : jsonSecond) {
////
////                                lectures.
////                                System.out.println(el.get("name").asText());
////
//                            }
//
//                        }
//                    }
//                }
//            });
//        }
//    }

    public JsonNode connectDisk(String prefix) {

        RequestEntity<Void> request = RequestEntity.get(URI.create("https://cloud-api.yandex.net/v1/disk/resources?path=ЧАТ-БОТ" + prefix))
                .header("Authorization", authorization)
                .build();

        try {
            return objectMapper.readTree(Objects.requireNonNull(restTemplate.exchange(request, JsonNode.class).getBody()).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    public byte[] downloadFile(JsonNode json) {
//
//        byte[] file = new byte[1024 * 12];
//
//        BufferedInputStream in;
//        try {
//
//            in = new BufferedInputStream(new URL(json.get("file").asText()).openStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            in.read(file, 0, file.length);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return file;
//    }
}


