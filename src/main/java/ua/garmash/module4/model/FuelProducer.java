package ua.garmash.module4.model;

import static ua.garmash.module4.service.DetailFactory.RANDOM;
import static ua.garmash.module4.service.DetailFactory.fuelBalance;

public class FuelProducer extends Thread {
    private final static int transportTime = 3000;
    private final static int producedFuelPerStepMin = 500;
    private final static int producedFuelPerStepMax = 1000;

    @Override
    public void run() {
        int producedFuelOnCurrentStep;
        while (!isInterrupted()) {
            try {
                producedFuelOnCurrentStep = RANDOM.nextInt(producedFuelPerStepMin,
                        producedFuelPerStepMax + 1);
                fuelBalance.addAndGet(producedFuelOnCurrentStep);
                Thread.sleep(transportTime);
            } catch (InterruptedException e) {
                if (!e.getMessage().equals("sleep interrupted")) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }
}
