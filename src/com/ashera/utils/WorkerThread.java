//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
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