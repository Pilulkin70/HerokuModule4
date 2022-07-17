package ua.garmash.module4.model;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.Main.RANDOM;

public class BasicStructureAssembler implements Runnable {
    private static final AtomicInteger commonProgressBasicOperations = new AtomicInteger(0);
    private final CyclicBarrier barrier;

    public BasicStructureAssembler(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        final int reloadTime = 2000;
        final int maxProgress = 100;
        while (commonProgressBasicOperations.get() < maxProgress) {
            try {
                commonProgressBasicOperations.addAndGet(RANDOM.nextInt(5, 11));
                System.out.println(Thread.currentThread().getName() + "  " + commonProgressBasicOperations.get());
                Thread.sleep(reloadTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        commonProgressBasicOperations.set(0);
    }
}
