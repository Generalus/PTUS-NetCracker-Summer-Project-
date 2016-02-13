package org.aksoftware.edu.udm.performance;


import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private long time;
    private List<ThreadResult> results;

    public TestResult() {
        results = new ArrayList<>();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<ThreadResult> getResults() {
        return results;
    }

    public void setResults(List<ThreadResult> results) {
        this.results = results;
    }

    public void printDefaultLog(){
        System.out.println("\nОбщее время выполнения задачи: " + getTime() + " ms\n");
        List<ThreadResult> list = getResults();
        long sumTime = 0;
        int successTasks = 0;
        for(int i = 0; i < list.size(); i++) {
            ThreadResult r = list.get(i);
            System.out.println(String.format("#%d %b (%dms), data: %s",
                    i, r.isSuccess(), r.getTime(), r.getData()));
            if (!r.isSuccess())
                System.out.println(r.getException().toString());
            else
                successTasks++;

            sumTime += r.getTime();
        }
        System.out.println("\nСуммарное время выполнения всех тасков: " + sumTime + " ms");
        System.out.println("Результат выполнения: " + successTasks * 100 / list.size() + "%");
    }
}
