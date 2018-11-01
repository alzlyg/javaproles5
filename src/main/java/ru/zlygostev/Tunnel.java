package ru.zlygostev;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore semaphore;
    public Tunnel(int raceParticipants) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        semaphore = new Semaphore(raceParticipants/2);
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getNameCar() + " готовится к этапу (ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getNameCar() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getNameCar() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}