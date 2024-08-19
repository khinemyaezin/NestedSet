package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;

public interface NodeComponentFactory {
    NodeComponent createCompositeNodeComponent();
    NodeComponent createLeafNodeComponent();
}
