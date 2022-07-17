package ua.garmash.module4.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.Main.RANDOM;
import static ua.garmash.module4.Main.fuelBalance;

public class FinalCollector implements Callable<Integer> {
    private static final AtomicInteger commonFinalAssemblyProgress = new AtomicInteger(0);

    @Override
    public Integer call() {
        final int reloadTime = 1000;
        final int maxProgress = 100;
        int fuelUsed = 0;
        int needFuel;
        while (commonFinalAssemblyProgress.get() < maxProgress) {
            try {
                needFuel = RANDOM.nextInt(350, 701);
                while (fuelBalance.get() < needFuel) {
                }
                fuelBalance.set(fuelBalance.get() - needFuel);
                commonFinalAssemblyProgress.addAndGet(10);
                fuelUsed += needFuel;
                System.out.println(Thread.currentThread().getName() + "  " + commonFinalAssemblyProgress.get());
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return fuelUsed;
    }
}
