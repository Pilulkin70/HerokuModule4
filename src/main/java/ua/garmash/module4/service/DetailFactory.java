package ua.garmash.module4.service;

import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.model.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DetailFactory {
    public static final AtomicInteger fuelBalance = new AtomicInteger(0);

    public static void produce() throws InterruptedException, ExecutionException {
        System.out.println("Lets do it!");
        DetailDao detailDao = new DetailDao();
        detailDao.save(new Detail.Builder()
                .startProduceFuel()
                .doBasicStructureAssembling()
                .doChipProgramming()
                .doFinalAssembling()
                .finalizeAndGetResult());
    }
}
