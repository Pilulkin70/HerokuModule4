package ua.garmash.module4.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.Main.RANDOM;

public class ChipProgrammer implements Callable<Integer> {
    private static final AtomicInteger commonProgressChipProgramming = new AtomicInteger(0);
    private static final int reloadTime = 1000;
    private static final int maxProgress = 100;
    @Override
    public Integer call() {

        int numberOfBrokenChips = 0;
        while (commonProgressChipProgramming.get() < maxProgress && !Thread.currentThread().isInterrupted()) {
            try {
                commonProgressChipProgramming.addAndGet(RANDOM.nextInt(25, 35));
                if (RANDOM.nextInt(0, 101) <= 30) {
                    ++numberOfBrokenChips;
                    commonProgressChipProgramming.set(0);
                }
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return numberOfBrokenChips;
    }


}
