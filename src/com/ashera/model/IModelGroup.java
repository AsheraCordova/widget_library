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
package com.ashera.model;

import java.util.List;

public interface IModelGroup extends IModel {
    List<String> getStableIds();
    String getModelFor();
    void setModelFor(String objValue);

    // add remove model method
    void addModel(Object model);
    void addAllModel(Object model);
    void addModel(Object model, int index);
    void removeModelAtIndex(int index);
    void removeModelById(String id);
    String getModelDescPath();
	void setModelDescPath(String modelDescPath);
}
