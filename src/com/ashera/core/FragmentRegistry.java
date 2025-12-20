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
package com.ashera.core;

import java.util.HashMap;
import java.util.Map;


public class FragmentRegistry {
    private final Map<String, IFragment> registry = new HashMap<>();
    private FragmentRegistry() {
    }

    // Singleton accessor
    public static FragmentRegistry getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final FragmentRegistry INSTANCE = new FragmentRegistry();
    }

    /**
     * Register a fragment (safe to call multiple times)
     */
    public void register(IFragment fragment) {
        String uid = fragment.getUId();
        if (uid == null) return;

        registry.put(uid, fragment);
    }

    /**
     * Lookup fragment by uid
     */
    public IFragment find(String uid) {
        IFragment fragment = registry.get(uid);
        if (fragment == null) {
            registry.remove(uid);
        }
        return fragment;
    }

    /**
     * Unregister explicitly
     */
    public void unregister(IFragment fragment) {
        registry.remove(fragment.getUId());
    }
}