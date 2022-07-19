package ua.garmash.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.*;

import static ua.garmash.module4.service.DetailFactory.fuelBalance;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private LocalTime startTimestamp;
    private LocalTime finishTimestamp;
    private int producedFuel;
    private int usedFuel;
    private int amountOfBrokenChips;
    private String status;

    public static class Builder {
        private final Detail detail;
        private FuelProducer fuelProducer;

        public Builder() {
            detail = new Detail();
            detail.setDate(LocalDate.now());
            detail.setStartTimestamp(LocalTime.now());
            detail.setStatus(DetailStatus.UNFINISHED.name());
        }

        public Builder startProduceFuel() {
            fuelProducer = new FuelProducer();
            fuelProducer.start();
            return this;
        }

        public Builder doBasicStructureAssembling() {
            final int numberOfBasicAssemblers = 2;
            final CyclicBarrier barrier = new CyclicBarrier(numberOfBasicAssemblers);
            final ExecutorService executor = Executors.newFixedThreadPool(numberOfBasicAssemblers);
            for (int i = 0; i < numberOfBasicAssemblers; i++) {
                executor.submit(new BasicStructureAssembler(barrier));
            }
            executorShutdown(executor);
            System.out.println("Base assembling done");
            return this;
        }

        public Builder doChipProgramming() throws ExecutionException, InterruptedException {
            final ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new ChipProgrammer());
            executorShutdown(executor);

            System.out.println("Programming done with " + future.get() + " broken chips");
            detail.setAmountOfBrokenChips(future.get());
            return this;
        }

        public Builder doFinalAssembling() throws ExecutionException, InterruptedException {
            final ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new FinalAssembler());
            executorShutdown(executor);

            detail.setFinishTimestamp(LocalTime.now());
            System.out.println("Final assembling done. Fuel used " + future.get());
            detail.setUsedFuel(future.get());
            detail.setProducedFuel(future.get() + fuelBalance.get());
            return this;
        }

        public Detail finalizeAndGetResult() {
            fuelProducer.interrupt();
            System.out.println("Fuel remaining " + fuelBalance.get());
            detail.setStatus(DetailStatus.FINISHED.name());
            return detail;
        }

        private void executorShutdown(ExecutorService executor) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(120, TimeUnit.SECONDS))
                        System.err.println("Pool did not terminate!");
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
