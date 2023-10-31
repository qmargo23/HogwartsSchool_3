package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService {
    @Value("${server.port}")
    private String currentPort;
    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public String getCurrentPort() {
        logger.info("Getting current port: {}", currentPort);
        return currentPort;
    }

    public String getSumTime_01() {
        logger.info("Was invoked method for getSumTime_01");
        long startTime = System.currentTimeMillis();

        Integer reduce = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long timeEnd = System.currentTimeMillis() - startTime;
        return "Sum1= " + reduce + " time: " + timeEnd;
    }

    public String getSumTime_02() {
        logger.info("Was invoked method for getSumTime_02");

        long timeBegin = System.currentTimeMillis();
        int n = 1_000_000;
        int[] arr = IntStream.rangeClosed(1, n).toArray();

//        ___________первый_вариант________________
//        int sum = IntStream.of(arr)
//                .parallel()//36
//                .sum();//21


        //_________2-й_вариант______________________
//        int sum = IntStream.of(arr)
//              .parallel()//36
//                .reduce(0,
//                        (a, b) -> {
////                            System.out.println("accumulator: a=" + a + " b=" + b);
//                            return a + b;//25
//                        }
//                );

        //__________третий_вариант_______
        int sum = IntStream.of(arr)
                .parallel()//31
                .reduce(0, Integer::sum);//26

        long timeEnd = System.currentTimeMillis() - timeBegin;
        return "Sum2= " + sum + " time: " + timeEnd;
    }
}
