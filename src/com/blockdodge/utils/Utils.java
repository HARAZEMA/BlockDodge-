package com.blockdodge.utils;

import java.awt.Color;
import java.util.Random;

public class Utils {
    private static Random random = new Random();

    public static Color randomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
