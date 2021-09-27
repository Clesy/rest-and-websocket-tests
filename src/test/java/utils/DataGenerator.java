package utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private static final Random random = new Random();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrsqtuvwxyz";
    private static final String NUMBER = "1234567890";

    private DataGenerator() {
    }

    public static String generateString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return stringBuilder.toString();
    }

    public static String generateNumberString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }
        return stringBuilder.toString();
    }

    public static int integer(int fromInclusive, int toExclusive){
        return ThreadLocalRandom.current().nextInt(fromInclusive, toExclusive);
    }
    public static String integerString(int fromInclusive, int toExclusive){
        return String.valueOf(integer(fromInclusive, toExclusive));
    }

    public static String emailGenerator(int length) {
        return "user" + generateString(length).toLowerCase() + "@example.com";
    }

    public static String generateIpAddress() {
        return randomInt() + "." + randomInt() + "." + randomInt() + "." + randomInt();
    }

    public static int randomInt() {
        return new Random().nextInt((255 - 1) + 1) + 1;
    }

    public static String generateUuid(){
        return UUID.randomUUID().toString();
    }

}
