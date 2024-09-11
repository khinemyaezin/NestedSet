package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import com.coolstuff.core.nestedset.repository.JpaNodeRepository;
import com.coolstuff.core.nestedset.repository.NodeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractNodeFunctions<T extends NodeComponent, ID> implements NodeFunctions<T, ID> {
    private final NodeRepository<T, ID> nodeRepository;
    private final JpaNodeRepository<T, ID> jpaNodeRepository;
    private final TreeBuilder treeBuilder;

    @Override
    public Optional<NodeComponent> getImmediateSubordinatesOf(ID nodeId) {
        List<T> nodeTreeList = nodeRepository.getNodeTreeList(nodeId);

        return Optional.of(nodeTreeList)
                .filter(list -> !list.isEmpty())
                .flatMap(this.treeBuilder::buildTree);
    }

    @Override
    public Optional<NodeComponent> getAllNodes() {
        List<T> nodeTreeList = this.jpaNodeRepository.findAllByOrderByLft();

        return Optional.of(nodeTreeList)
                .filter(list -> !list.isEmpty())
                .flatMap(this.treeBuilder::buildTree);
    }

    @Override
    public Optional<NodeComponent> findDescendantsOf(ID nodeID){
        return this.jpaNodeRepository.findById(nodeID)
                .map(t -> this.nodeRepository.findChildren(t.getLft(), t.getRgt()))
                .filter(list -> !list.isEmpty())
                .flatMap(this.treeBuilder::buildTree);
    }
    @Override
    public Optional<NodeComponent> findParentOf(ID id){
        List<T> nodeList = this.nodeRepository.findParentOf(id);

        return Optional.of(nodeList)
                .filter(list -> !list.isEmpty())
                .flatMap(this.treeBuilder::buildTree)
                .map(this.treeBuilder::getLeafList)
                .filter(list-> !list.isEmpty())
                .map(List::getFirst);
    }
}
