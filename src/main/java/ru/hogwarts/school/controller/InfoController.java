package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

import java.util.concurrent.ForkJoinPool;

@RestController
@RequestMapping("/info")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return infoService.getCurrentPort();
    }



    ForkJoinPool pool = ForkJoinPool.commonPool();
    @GetMapping("/count-sum-without-improvement")
    public String getSumTime_01() {
        return infoService.getSumTime_01();
    }

    @GetMapping("/count-sum-with-improvement")
    public String getSumTime_02() {
        return infoService.getSumTime_02();
//        return pool.getParallelism();
    }



}
