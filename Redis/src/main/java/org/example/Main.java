package org.example;


import java.util.Random;

public class Main {
    private static final int REGISTERED_USER_AMOUNT = 20;
    private static final int SLEEP = 1;
    private static final Random rand = new Random ();
    private static void log(int userId) {
        String log = String.format("На главной странице показываем пользователя: %d",  userId);
        System.out.println(log);
    }
    public static void main(String[] args) throws InterruptedException{
        RedisStorage redisStorage = new RedisStorage();
        redisStorage.init();
        while (true) {
            int rn1 = rand.nextInt(0,REGISTERED_USER_AMOUNT/2);
            int rn2 = rand.nextInt(REGISTERED_USER_AMOUNT/2, REGISTERED_USER_AMOUNT);
            log(rn1);
            for (int i = 0; i < rn1; i++) {
                log(i);
            }
            for (int i = rn1 + 1; i < rn2; i++) {
                log(i);
            }
            log(rn2);
            for (int i = rn2 + 1; i < REGISTERED_USER_AMOUNT; i++) {
                log(i);
            }
            Thread.sleep(SLEEP);
        }
    }
}