package ru.zlygostev;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class Car extends Thread {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch latchReady;
    private CountDownLatch latchFinish;
    public String getNameCar() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch latchReady, CountDownLatch latchFinish) {
        this.race = race;
        this.speed = speed;
        this.latchReady = latchReady;
        this.latchFinish = latchFinish;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            latchReady.countDown();
            latchReady.await();
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        latchFinish.countDown();
    }
}
