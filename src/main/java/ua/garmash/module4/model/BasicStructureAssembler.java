package ua.garmash.module4.model;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static ua.garmash.module4.service.DetailFactory.RANDOM;

public class BasicStructureAssembler implements Runnable {
    private static final AtomicInteger commonProgressBasicOperations = new AtomicInteger(0);
    private static final int reloadTime = 2000;
    private static final int maxProgress = 100;
    private static final int pointsToStepMin = 5;
    private static final int pointsToStepMax = 10;
    private final CyclicBarrier barrier;

    public BasicStructureAssembler(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (commonProgressBasicOperations.get() < maxProgress && !Thread.currentThread().isInterrupted()) {
            commonProgressBasicOperations.addAndGet(RANDOM.nextInt(pointsToStepMin, pointsToStepMax + 1));
            try {
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
