package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import com.coolstuff.core.nestedset.repository.JpaNodeRepository;
import com.coolstuff.core.nestedset.repository.NodeRepository;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractNodeFunctions<T extends NodeComponent, ID> implements NodeFunctions<T, ID> {
    private final NodeRepository<T, ID> nodeRepository;
    private final JpaNodeRepository<T, ID> jpaNodeRepository;
    private final TreeBuilder treeBuilder;

    @Override
    public Optional<NodeComponent> getImmediateSubordinatesOf(ID nodeId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> nodeTreeList = nodeRepository.getNodeTreeList(nodeId);
        if (nodeTreeList.isEmpty()) return Optional.empty();

        return this.treeBuilder.buildTree(nodeTreeList);
    }

    @Override
    public Optional<NodeComponent> getAllNodes() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        List<T> nodeTreeList = this.jpaNodeRepository.findAllByOrderByLft();
        if (nodeTreeList.isEmpty()) return Optional.empty();

        return this.treeBuilder.buildTree(nodeTreeList);
    }

    @Override
    public Optional<NodeComponent> findDescendantsOf(ID nodeID) throws Exception {
        T node = this.jpaNodeRepository.findById(nodeID).orElseThrow(() -> new Exception("Node is not found"));
        List<T> children = this.nodeRepository.findChildren(node.getLft(), node.getRgt());
        return this.treeBuilder.buildTree(children);
    }
    @Override
    public Optional<NodeComponent> findParentOf(ID id) throws Exception {
        List<T> nodeList = this.nodeRepository.findParentOf(id);
        Optional<NodeComponent> node = this.treeBuilder.buildTree(nodeList);
        if(node.isPresent()) {
            NodeComponent parent = this.treeBuilder.getLeafList(node.get()).getFirst();
            return Optional.of(parent);
        } else {
            return Optional.empty();
        }
    }
}
