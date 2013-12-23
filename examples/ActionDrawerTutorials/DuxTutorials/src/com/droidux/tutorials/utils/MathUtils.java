package com.droidux.tutorials.utils;

public final class MathUtils {
    public static float constrain(float number, float min, float max) {
        return number<min ? min: (number>max?max:number);
    }
}
