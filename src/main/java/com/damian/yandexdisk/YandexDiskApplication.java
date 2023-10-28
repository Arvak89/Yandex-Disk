package com.damian.yandexdisk;

import com.damian.yandexdisk.service.Disk;
import com.damian.yandexdisk.store.entities.Lecture;
import com.damian.yandexdisk.store.entities.Time;
import com.damian.yandexdisk.store.repositories.LectureRepo;
import com.damian.yandexdisk.store.repositories.TimeRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YandexDiskApplication implements CommandLineRunner {

    //    FileRepo fileRepo;
    Disk disk;
    LectureRepo lectureRepo;
    ObjectMapper objectMapper;
    TimeRepo timeRepo;
    RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(YandexDiskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        RestTemplate restTemplate = new RestTemplate();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        RequestEntity<Void> request = RequestEntity.get(URI.create("https://cloud-api.yandex.net/v1/disk/resources?path=ЭБНЭ/x.xlsx"))
//                .header("Authorization", " OAuth y0_AgAAAAA_BxKeAAq3rwAAAADwFGqC0laKbcyUSOeKpzZI1nG-OiSzqv8")
//                .build();
//
//        ResponseEntity<JsonNode> response = restTemplate.exchange(request, JsonNode.class);
//
//        JsonNode json = objectMapper.readTree(response.getBody().toString());
//
//        byte[] file = new byte[1024 * 12];
//
//        BufferedInputStream in = new BufferedInputStream(new URL(json.get("file").asText()).openStream());
//
//        in.read(file, 0, file.length);
//
//        lectureRepo.save(Lecture.builder()
//                .file(file)
//                .fileName(json.get("name").asText())
//                .build());
//        JsonNode json = disk.connectDisk();
//
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    disk.saveFile(json);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//
//        Timer timer = new Timer();
//
//        long delay = 1000l;
//        timer.schedule(task,0, delay);

//        RequestEntity<Void> request = RequestEntity.get(URI.create("https://cloud-api.yandex.net/v1/disk/resources?path=ЭБНЭ/Lectures"))
//                .header("Authorization", " OAuth y0_AgAAAAA_BxKeAAq3rwAAAADwFGqC0laKbcyUSOeKpzZI1nG-OiSzqv8")
//                .build();
//
//        System.out.println(objectMapper.readTree(restTemplate.exchange(request, JsonNode.class).getBody().toString()).get("_embedded").get("items").get(0).get("file"));
//        timeRepo.save(Time.builder()
//                .time(new Date())
//                .id(1L)
//                .build());
//
        disk.checkNewFiles();

    }
}
