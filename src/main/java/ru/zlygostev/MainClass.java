package ru.zlygostev;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 8;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        final CountDownLatch latchReady = new CountDownLatch(CARS_COUNT);
        final CountDownLatch latchFinish = new CountDownLatch(CARS_COUNT);
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), latchReady, latchFinish);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            latchReady.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            latchFinish.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
