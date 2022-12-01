package com.ashera.utils;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class WorkerThread implements Runnable {
    private final CountDownLatch countDownLatch;
    private String url;
    private String key;
    private Map<String, String> resultMap;


    public WorkerThread(String url, CountDownLatch countDownLatch, String key, Map<String, String> resultMap) {
        this.url = url;
        this.countDownLatch = countDownLatch;
        this.key = key;
        this.resultMap = resultMap;
    }


    @Override
    public void run() {
        String data = FileUtils.readStringFromURL(url);
        countDownLatch.countDown();
        resultMap.put(key, data);
    }

}