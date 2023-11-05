package com.damian.yandexdisk;

import com.damian.yandexdisk.service.Disk;
import com.damian.yandexdisk.service.LectureService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YandexDiskApplication implements CommandLineRunner {

    Disk disk;
    LectureService lectureService;

    public static void main(String[] args) {
        SpringApplication.run(YandexDiskApplication.class, args);
    }

    @Override
    public void run(String... args){
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//
//                disk.checkNewFiles();
//
//
//
//            }
//        };
//
//        Timer timer = new Timer();
//
//        long delay = 1000L;
//        timer.schedule(task,0, delay);

//        disk.removeOldFiles();

//        lectureService.saveLecture(121233, "1321312sdas", new byte[] {});
//        lectureService.saveLecture(121123233, "132131asdas2sdas", new byte[] {});
//        lectureService.saveLecture(121231213, "1321asd12sdas", new byte[] {});
//        lectureService.saveLecture(121233, "13213asd12sdas", new byte[] {});
//        System.out.println(lectureService.removeLecture(52));

        disk.checkNewFiles();

    }
}
