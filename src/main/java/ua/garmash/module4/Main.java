package ua.garmash.module4;
/*
Реализовать веб приложение фабрику, которое содержит 3 страницы (на 10 через сервлеты):
/stats – отображает общую статистику о
    ●	количестве изготовленных изделий
    ●	количестве сломанных микросхем
    ●	добытом топливе
/stats/{id} – отображает информацию о конкретной детали:
    ●	дата проведения
    ●	количество затраченного времени
    ●	количество добытого топлива
    ●	количество сломанных микросхем
/race/start – страница запуска создания детали

Данные хранятся в  SQL DB и должны сохраняться между запусками приложения.

Механизм работы фабрики
На фабрике присутствует 5 роботов, каждый из которых делает свою порцию работы (потоки)
●	Робот 1 - делает фоновую работу - добывает топливо для фабрики
    ○	За одну итерация робот может добыть 500-1000 галлонов топлива, после чего тратит 3 секунд на транспортировку.
●	Робот 2 и Робот 3 - собирают базовую конструкцию детали
    ○	За одну итерацию они могут выполнить работу на 10-20 пунктов
    ○	После чего происходит перезагрузка на 2 секунд
    ○	Работа считается законченной, когда достигнуто 100+ пунктов
●	Робот 4 - начинает свою работу после завершения работы Роботов 2 и 3
    ○	За одну итерацию робот программирует микросхемы в диапазоне 25-35 пунктов
    ○	Перезагрузка 1 секунды
    ○	С вероятностью 30% робот может сломать микросхему и ему придется начать заново весь процесс работы
    ○	Работа считается законченной, когда достигнуто 100+ пунктов
●	Робот 5 - формирует деталь в готовый вид, но для этого ему нужно топливо, добытое роботом 1
    ○	За одну итерацию робот продвигается на 10 пунктов
    ○	Перезагрузка 1 секунды
    ○	Одна итерация требует 350-700 топлива
    ○	Если топлива нет, то робот засыпает до пополнения резервов
    ○	Работа считается законченной, когда достигнуто 100+ пунктов
*/

import ua.garmash.module4.config.HibernateFactoryUtil;
import ua.garmash.module4.dao.DetailDao;
import ua.garmash.module4.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final Random RANDOM = new Random();
    public static final AtomicInteger fuelBalance = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        HibernateFactoryUtil.init();
        System.out.println("Lets do it!");
        DetailDao detailDao = new DetailDao();
        Detail detail = new Detail();
        detail.setDate(LocalDate.now());
        detail.setStartTimestamp(LocalTime.now());

        FuelProducer fuelProducer = new FuelProducer();
        fuelProducer.start();

        final int numberOfBasicAssemblers = 2;
        final CyclicBarrier barrier = new CyclicBarrier(numberOfBasicAssemblers);
        final ExecutorService executor = Executors.newFixedThreadPool(numberOfBasicAssemblers);
        for (int i = 0; i < numberOfBasicAssemblers; i++) {
            executor.submit(new BasicStructureAssembler(barrier));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(20, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate!");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Assembling done");

        final ExecutorService executor1 = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor1.submit(new ChipProgrammer());
        executor1.shutdown();
        try {
            if (!executor1.awaitTermination(20, TimeUnit.SECONDS)) {
                executor1.shutdownNow();
                if (!executor1.awaitTermination(20, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate!");
            }
        } catch (InterruptedException ie) {
            executor1.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Programming done with " + future.get() + " broken chips");
        detail.setAmountOfBrokenChips(future.get());

        final ExecutorService executor2 = Executors.newSingleThreadExecutor();
        Future<Integer> future2 = executor2.submit(new FinalCollector());
        executor2.shutdown();
        try {
            if (!executor2.awaitTermination(60, TimeUnit.SECONDS)) {
                executor2.shutdownNow();
                if (!executor2.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate!");
            }
        } catch (InterruptedException ie) {
            executor2.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Final assembling done. Fuel used " + future2.get());
        detail.setFinishTimestamp(LocalTime.now());
        detail.setUsedFuel(future2.get());


//        Thread.sleep(5000);
        fuelProducer.interrupt();
        System.out.println(fuelBalance.get());

        detailDao.save(detail);
    }
}
