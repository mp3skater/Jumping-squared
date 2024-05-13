package net.mp3skater.main.utils;

public class Col_Utils {
    public static int absRound(double number) {
        return number > 0 ? (int) Math.ceil(number) : (int) Math.floor(number);
    }
}
