package com.damian.yandexdisk;

import com.damian.yandexdisk.service.Disk;
import com.damian.yandexdisk.service.LectureService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YandexDiskApplication implements CommandLineRunner {

    Disk disk;

    public static void main(String[] args) {
        SpringApplication.run(YandexDiskApplication.class, args);
    }

    @Override
    public void run(String... args){

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                disk.removeOldFiles();
                disk.checkNewFiles();

            }
        };

        Timer timer = new Timer();
        long delay = (1000 * 60);
        timer.schedule(task, 0, delay);

//        timeRepo.save(Time.builder()
//                .time(new Date(1))
//                .id(1L)
//                .build());

//        disk.removeOldFiles();

//        lectureService.saveLecture(121233, "1321312sdas", new byte[] {});
//        lectureService.saveLecture(121123233, "132131asdas2sdas", new byte[] {});
//        lectureService.saveLecture(121231213, "1321asd12sdas", new byte[] {});
//        lectureService.saveLecture(121233, "13213asd12sdas", new byte[] {});

//        disk.checkNewFiles();

    }
}
