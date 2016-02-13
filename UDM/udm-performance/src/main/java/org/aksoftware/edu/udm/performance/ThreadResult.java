package org.aksoftware.edu.udm.performance;


import java.util.List;

public class ThreadResult {
    private boolean isSuccess;

    private long time;

    private List<String> data; // сгенерированные строки для этого таска в порядке генерирования

    private Exception exception;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ThreadResult() {
    }

    public ThreadResult(boolean isSuccess, long time, Exception exception, List<String> data) {
        this.isSuccess = isSuccess;
        this.time = time;
        this.exception = exception;
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
