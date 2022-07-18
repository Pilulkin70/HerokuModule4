package ua.garmash.module4.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.service.DetailFactory.RANDOM;
import static ua.garmash.module4.service.DetailFactory.fuelBalance;

public class FinalCollector implements Callable<Integer> {
    private static final AtomicInteger commonFinalAssemblyProgress = new AtomicInteger(0);
    private static final int reloadTime = 1000;
    private static final int maxProgress = 100;
    private static final int needFuelPerStepMin = 350;
    private static final int needFuelPerStepMax = 700;
    private static final int pointsToStep = 10;

    @Override
    public Integer call() {
        int fuelUsed = 0;
        int needFuel;
        while (commonFinalAssemblyProgress.get() < maxProgress && !Thread.currentThread().isInterrupted()) {
            try {
                needFuel = RANDOM.nextInt(needFuelPerStepMin, needFuelPerStepMax + 1);
                while (fuelBalance.get() < needFuel && !Thread.currentThread().isInterrupted()) {
                }
                fuelBalance.set(fuelBalance.get() - needFuel);
                commonFinalAssemblyProgress.addAndGet(pointsToStep);
                fuelUsed += needFuel;
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return fuelUsed;
    }
}
