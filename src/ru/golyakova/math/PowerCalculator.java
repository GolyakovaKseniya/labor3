package ru.golyakova.math;

import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;
public class PowerCalculator {
    public static double calculatePower(String xStr, String yStr){
        int x = parseInt(xStr);
        int y = parseInt(yStr);
        return pow(x,y);
    }
}
