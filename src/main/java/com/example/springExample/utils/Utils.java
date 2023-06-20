package com.example.springExample.utils;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
public class Utils {
    public int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public String randomGender(String[] genderArray) {
        Random random = new Random();
        int number = genderArray.length;
        number = number - 1;
        number = random.nextInt(number + 1);
        return genderArray[number];
    }
}
