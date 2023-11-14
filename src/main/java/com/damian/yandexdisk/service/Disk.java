package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                            String name = el.get("name").asText();
                            String file = el.get("file").asText();
                            switch (dir) {
                                case "1)Lectures": {
                                    lectureService.saveLecture(
                                            Integer.parseInt(name.substring(2, 6)),
                                            name,
                                            file
                                    );
                                    break;
                                }
                                case "2)Practices": {
                                    practiceService.savePractice(
                                            Integer.parseInt(name.substring(2, 6)),
                                            name,
                                            file
                                    );
                                    break;
                                }
                                case "3)Tasks": {
                                    taskService.saveTask(
                                            name,
                                            name.substring(name.indexOf("-") + 1, name.indexOf(".")),
                                            file
                                    );
                                    break;
                                }
                                case "4)Materials": {
                                    materialService.saveMaterial(
                                            name,
                                            file,
                                            Long.valueOf(name.substring(0, 1))
                                    );
                                    break;
                                }
                                case "5)Questions": {
                                    questionService.saveQuestion(
                                            name,
                                            file
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
    public void removeOldFiles() {
        JsonNode json = connectDisk("").get("_embedded").get("items");

        if (!json.isEmpty()) {

            json.forEach((e) -> {

                if (e.get("type").asText().equals("dir")) {

                    String dir = e.get("name").asText();

                    switch (dir) {
                        case "1)Lectures": {

                            List<String> lectures = lectureService.fetchAll().stream().map(Lecture::getFileName).collect(Collectors.toList());
                            String url = "/" + e.get("name").toString().replaceAll("\"", "");

                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                            for (JsonNode el : jsonSecond) {
                                for (String lecture : lectures) {
                                    if (el.get("name").asText().equals(lecture)) {
                                        lectures.remove(lecture);
                                        break;
                                    }
                                }

                                if (!lectures.isEmpty()) {
                                    lectures.forEach(lectureService::removeLecture);
                                }
                            }
                        }
                        case "2)Practices": {
                            List<String> practices = practiceService.fetchAll().stream().map(Practice::getFileName).collect(Collectors.toList());
                            String url = "/" + e.get("name").toString().replaceAll("\"", "");

                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                            for (JsonNode el : jsonSecond) {
                                for (String practice : practices) {
                                    if (el.get("name").asText().equals(practice)) {
                                        practices.remove(practice);
                                        break;
                                    }
                                }

                                if (!practices.isEmpty()) {
                                    practices.forEach(practiceService::removePractice);
                                }
                            }
                        }
                        case "4)Materials": {
                            List<String> materials = materialService.fetchAll().stream().map(Material::getFileName).collect(Collectors.toList());
                            String url = "/" + e.get("name").toString().replaceAll("\"", "");

                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                            for (JsonNode el : jsonSecond) {
                                for (String material : materials) {
                                    if (el.get("name").asText().equals(material)) {
                                        materials.remove(material);
                                        break;
                                    }
                                }

                                if (!materials.isEmpty()) {
                                    materials.forEach(materialService::removeMaterial);
                                }
                            }
                        }
                        case "5)Questions": {
                            List<String> questions = questionService.fetchAll().stream().map(Question::getFileName).collect(Collectors.toList());
                            String url = "/" + e.get("name").toString().replaceAll("\"", "");

                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                            for (JsonNode el : jsonSecond) {
                                for (String question : questions) {
                                    if (el.get("name").asText().equals(question)) {
                                        questions.remove(question);
                                        break;
                                    }
                                }

                                if (!questions.isEmpty()) {
                                    questions.forEach(questionService::removeQuestion);
                                }
                            }
                        }
                        case "3)Tasks": {
                            List<String> tasks = taskService.fetchAll().stream().map(Task::getFileName).collect(Collectors.toList());
                            String url = "/" + e.get("name").toString().replaceAll("\"", "");

                            JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                            for (JsonNode el : jsonSecond) {
                                for (String task : tasks) {
                                    if (el.get("name").asText().equals(task)) {
                                        tasks.remove(task);
                                        break;
                                    }
                                }

                                if (!tasks.isEmpty()) {
                                    tasks.forEach(taskService::removeTask);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

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


