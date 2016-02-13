package org.aksoftware.edu.udm.performance;

import org.aksoftware.edu.udm.UdmManager;

import org.aksoftware.edu.udm.jdbc.UdmManagerJDBC;
import org.aksoftware.edu.udm.jpa.UdmManagerJPA;
import org.aksoftware.edu.udm.performance.tasks.AbstractTask;
import org.aksoftware.edu.udm.performance.tasks.EqualsTask;

import java.util.*;
import java.util.concurrent.*;

public enum CommonTest {
    JPA(UdmManagerJPA.MySQL),
    JDBC(UdmManagerJDBC.getInstance());

    private UdmManager manager;

    private volatile Set<String> randomStrings = new HashSet<>();

    CommonTest(UdmManager manager) {
        this.manager = manager;
    }

    public TestResult equalsTest(int count, int threadNumber, boolean showProgress) throws Exception{
        EqualsTask task = new EqualsTask(manager, randomStrings, showProgress);
        return test(task, count, threadNumber, showProgress);
    }

    private TestResult test(AbstractTask task, int count, int threadNumber, final boolean showProgress) throws Exception{
        generateRandomStrings(count * 10, 5, showProgress);

        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        System.out.println("Прогресс тестирования:\n");

        Date before = new Date();

        List<Callable<ThreadResult>> tasks = new ArrayList<>();

        for(int j = 0; j < count; j++) {
            task.setCountDownLatch(countDownLatch);
            tasks.add(task);
        }

        List<Future<ThreadResult>> futures = service.invokeAll(tasks);
        countDownLatch.await();

        TestResult result = new TestResult();
        for(Future<ThreadResult> future: futures)
            result.getResults().add(future.get());
        result.setTime(new Date().getTime() - before.getTime());

        if (showProgress)
            System.out.println();

        service.shutdown();
        manager.close();
        System.gc();
        return result;
    }

    public void generateRandomStrings(int count, int length, boolean showProgress){
        if (randomStrings.size() >= count) return;
        if (showProgress)
            System.out.println("Выполняется генерация строк...\n");

        String symbols = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        int maxPosition = symbols.length() - 1;
        int k = 0;
        while (randomStrings.size() < count){
            k++;
            if (showProgress && k % 200 == 0)
                System.out.print("|");
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < length; j++) {
                int position = (int) Math.floor(Math.random() * maxPosition);
                result.append(symbols.charAt(position));
            }
            randomStrings.add(result.toString());
        }
        if (showProgress)
            System.out.println("\nСгенерировано " + k + " строк!\n");
    }

}
