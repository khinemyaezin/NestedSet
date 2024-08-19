package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import com.coolstuff.core.nestedset.repository.JpaNodeRepository;
import com.coolstuff.core.nestedset.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class NodeTemplate<T extends NodeComponent, ID> implements NodeService<T, ID> {
    private final NodeRepository<T,ID> nodeRepository;
    private final JpaNodeRepository<T, ID> jpaNodeRepository;

    @Override
    @Transactional
    public T createNode(String name) {
        Integer right = nodeRepository.findMaxRight();
        if (right == null) {
            right = 0;
        }
        right++;
        T node = this.buildNodeEntity(name, right, right + 1, 0);
        return jpaNodeRepository.save(node);
    }

    @Override
    @Transactional
    public T createNode(String name, ID parentId) {
        T rootNode = jpaNodeRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent not found"));
        Integer right = rootNode.getRgt();

        nodeRepository.incrementLeftBoundaryAfter(right);
        nodeRepository.incrementRightBoundaryAfter(right);

        T node = this.buildNodeEntity(name, right, right + 1, rootNode.getDepth() + 1);
        return jpaNodeRepository.save(node);
    }

    @Override
    @Transactional
    public void deleteNode(ID id) {
        T category = jpaNodeRepository.findById(id).orElseThrow(() -> new RuntimeException("Node not found"));
        Integer left = category.getLft();
        Integer right = category.getRgt();
        Integer width = right - left + 1;

        nodeRepository.deleteNodesInRange(left, right);

        nodeRepository.decrementRightBoundaryAfter(right, width);
        nodeRepository.decrementLeftBoundaryAfter(right, width);
    }

    public abstract T buildNodeEntity(String name, Integer lft, Integer rgt, Integer depth);

    public abstract T buildNodeEntity(Long id, String name, Integer lft, Integer rgt, Integer depth);

}