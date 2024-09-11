package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

import java.util.List;
import java.util.Optional;

public interface NodeService<T extends NodeComponent,ID> {
    T createNode(String name);

    T createNode(String name, ID parentId);

    Optional<T> readNode(ID id);

    T updateNode(ID id, String newName);

    void deleteNode(ID id);

}
