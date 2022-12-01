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
}
