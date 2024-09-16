package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface TreeBuilder {
    Optional<NodeComponent> buildTree(List<NodeComponent> nodeList );
    List<NodeComponent> getLeafList(NodeComponent node);
}
