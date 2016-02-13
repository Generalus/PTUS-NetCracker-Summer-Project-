package org.aksoftware.edu.udm.performance.tasks;


import org.aksoftware.edu.udm.UdmManager;
import org.aksoftware.edu.udm.performance.ThreadResult;
import org.aksoftware.edu.udm.performance.exceptions.NeedMoreStringsException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractTask implements Callable<ThreadResult> {
    private UdmManager manager;
    private volatile Set<String> randomStrings;
    private boolean showProgress;
    private CountDownLatch countDownLatch;

    public UdmManager getManager() {
        return manager;
    }

    public void setManager(UdmManager manager) {
        this.manager = manager;
    }

    public Set<String> getRandomStrings() {
        return randomStrings;
    }

    public void setRandomStrings(Set<String> randomStrings) {
        this.randomStrings = randomStrings;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public synchronized String getRandomString() throws NeedMoreStringsException{
        String result;
        Iterator<String> iterator = randomStrings.iterator();
        if (iterator.hasNext()) {
            result = iterator.next();
            iterator.remove();
        } else
            throw new NeedMoreStringsException("Сгененировано недостаточно много рандомных строк!");
        return result;
    }

    public abstract ThreadResult call() throws Exception;
}
