package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

import java.util.Optional;

public interface NodeFunctions<T extends NodeComponent, ID> {
    Optional<NodeComponent> findImmediateChildren(ID nodeId);

    Optional<NodeComponent> getAllNodes();

    Optional<NodeComponent> findChilderenOf(ID nodeId) throws Exception;

    Optional<NodeComponent> findParentOf(ID id) throws Exception;
}
