package ua.garmash.module4.service;

import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.model.*;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DetailFactory {
    public static final Random RANDOM = new Random();
    public static final AtomicInteger fuelBalance = new AtomicInteger(0);
    public static volatile boolean producingInProgress = false;

    public static void produce() {
        System.out.println("Lets do it!");
        if (!producingInProgress) {
            producingInProgress = true;
            fuelBalance.set(0);
            DetailDao detailDao = new DetailDao();
            try {
                detailDao.save(new Detail.Builder()
                        .startProduceFuel()
/*                        .doBasicStructureAssembling()
                        .doChipProgramming()*/
                        .doFinalAssembling()
                        .finalizeAndGetResult());
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            producingInProgress = false;
        }
    }
}
