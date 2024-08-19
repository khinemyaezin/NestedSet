package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TreeBuilder {
    <T extends NodeComponent> NodeComponent buildTree(List<T> nodeList ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    <T extends NodeComponent> List<NodeComponent> getLeafList(T node);
}
