package com.damian.yandexdisk.service;

import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Time;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.TimeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class Disk {

    LectureService lectureService;
    ObjectMapper objectMapper;
    TimeRepo timeRepo;
    RestTemplate restTemplate = new RestTemplate();

    public void checkNewFiles() throws JsonProcessingException {

        List<Time> time = timeRepo.findAll();

        JsonNode json = connectDisk("").get("_embedded").get("items");

        if (!json.isEmpty()) {

            json.forEach((e) -> {

                if (e.get("type").asText().equals("dir")) {

                    String dir = e.get("name").asText();
                    String url = "/" + e.get("name").toString().replaceAll("\"", "");

                    JsonNode jsonSecond = connectDisk(url).get("_embedded").get("items");

                    jsonSecond.forEach((el) -> {

                        lectureService.saveLecture(
                                2134,
                                "asdasdas",
                                downloadFile(el)
                        );

                        if (time.get(0).getTime().after(new Date())) {

                            switch (dir) {
                                case "Задачи": {


                                    break;
                                }
                                case "Лекции": {

                                    lectureService.saveLecture(
                                            2134,
                                            "asdasdas",
                                            downloadFile(el)
                                    );

                                    break;
                                }
                                case "Практики": {
                                    break;
                                }
                            }

                        }
                    });
                }
            });
        }
    }

    public JsonNode connectDisk(String prefix) {

        RequestEntity<Void> request = RequestEntity.get(URI.create("https://cloud-api.yandex.net/v1/disk/resources?path=ЭБНЭ" + prefix))
                .header("Authorization", " OAuth y0_AgAAAAA_BxKeAAq3rwAAAADwFGqC0laKbcyUSOeKpzZI1nG-OiSzqv8")
                .build();

        try {
            return objectMapper.readTree(Objects.requireNonNull(restTemplate.exchange(request, JsonNode.class).getBody()).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadFile(JsonNode json) {

        byte[] file = new byte[1024 * 12];

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new URL(json.get("file").asText()).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            in.read(file, 0, file.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}


