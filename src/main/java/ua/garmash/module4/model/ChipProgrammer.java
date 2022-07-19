package ua.garmash.module4.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.service.DetailFactory.RANDOM;

public class ChipProgrammer implements Callable<Integer> {
    private static final AtomicInteger commonProgressChipProgramming = new AtomicInteger(0);
    private static final int reloadTime = 1000;
    private static final int maxProgress = 100;
    private static final int pointsToStepMin = 25;
    private static final int pointsToStepMax = 35;
    private static final int percentOfBrokenChips = 30;

    @Override
    public Integer call() {
        int numberOfBrokenChips = 0;
        while (commonProgressChipProgramming.get() < maxProgress && !Thread.currentThread().isInterrupted()) {
            try {
                commonProgressChipProgramming.addAndGet(RANDOM.nextInt(pointsToStepMin, pointsToStepMax + 1));
                if (RANDOM.nextInt(0, 101) <= percentOfBrokenChips) {
                    ++numberOfBrokenChips;
                    commonProgressChipProgramming.set(0);
                }
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        commonProgressChipProgramming.set(0);
        return numberOfBrokenChips;
    }


}
