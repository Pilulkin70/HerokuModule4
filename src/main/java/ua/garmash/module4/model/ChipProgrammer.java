package ua.garmash.module4.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.Main.RANDOM;

public class ChipProgrammer implements Callable<Integer> {
    private static final AtomicInteger commonProgressChipProgramming = new AtomicInteger(0);

    @Override
    public Integer call() throws Exception {
        final int reloadTime = 1000;
        final int maxProgress = 100;
        int numberOfBrokenChips = 0;
        while (commonProgressChipProgramming.get() < maxProgress) {
            try {
                commonProgressChipProgramming.addAndGet(RANDOM.nextInt(25, 35));
                if (RANDOM.nextInt(0, 101) <= 30) {
                    ++numberOfBrokenChips;
                    commonProgressChipProgramming.set(0);
                }
                System.out.println(Thread.currentThread().getName() + "  " + commonProgressChipProgramming.get());
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return numberOfBrokenChips;
    }


}
