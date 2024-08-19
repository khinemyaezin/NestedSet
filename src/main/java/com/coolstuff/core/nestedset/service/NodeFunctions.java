package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface NodeFunctions<T extends NodeComponent, ID> {
    Optional<NodeComponent> getImmediateSubordinatesOf(ID nodeId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Optional<NodeComponent> getAllNodes() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Optional<NodeComponent> findDescendantsOf(ID nodeId) throws Exception;

    Optional<NodeComponent> findParentOf(ID id) throws Exception;
}
