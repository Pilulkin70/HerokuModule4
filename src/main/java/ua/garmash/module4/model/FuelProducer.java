package ua.garmash.module4.model;

import static ua.garmash.module4.Main.RANDOM;
import static ua.garmash.module4.Main.fuelBalance;

public class FuelProducer extends Thread {
    @Override
    public void run() {
        final int transportTime = 3000;
        System.out.println("Fuel is " + fuelBalance.get());
        while (!isInterrupted()) {
            try {
                fuelBalance.addAndGet(RANDOM.nextInt(500, 1001));
                Thread.sleep(transportTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
