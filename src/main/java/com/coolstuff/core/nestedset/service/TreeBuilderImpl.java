package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TreeBuilderImpl implements TreeBuilder {
    private final NodeComponentFactory nodeComponentFactory;

    public <T extends NodeComponent> Optional<NodeComponent> buildTree(List<T> nodeList) {
        if (nodeList == null || nodeList.isEmpty()) {
            return Optional.empty();
        }

        T root = nodeList.getFirst();
        NodeComponent node;
        if (root.getRgt() == root.getLft() + 1) {
            node = nodeComponentFactory.createLeafNodeComponent();
            node.setId(root.getId());
            node.setName(root.getName());
            node.setLft(root.getLft());
            node.setRgt(root.getRgt());
            node.setDepth(root.getDepth());
            return Optional.of(node);
        } else {
            node = nodeComponentFactory.createCompositeNodeComponent();
            node.setId(root.getId());
            node.setName(root.getName());
            node.setLft(root.getLft());
            node.setRgt(root.getRgt());
            node.setDepth(root.getDepth());
            return Optional.of(buildTreeRecursive(node, nodeList,0));
        }
    }

    private <T extends NodeComponent> NodeComponent buildTreeRecursive(NodeComponent parent, List<T> nodeList, int index) {
        int i = index + 1;
        while (i < nodeList.size() && nodeList.get(i).getLft() < parent.getRgt()) {
            T child = nodeList.get(i);
            if (child.getDepth() == parent.getDepth() + 1) {
                NodeComponent node;
                if (child.getRgt() == child.getLft() + 1) {
                    node = nodeComponentFactory.createLeafNodeComponent();
                    node.setId(child.getId());
                    node.setName(child.getName());
                    node.setLft(child.getLft());
                    node.setRgt(child.getRgt());
                    node.setDepth(child.getDepth());
                } else {
                    node = nodeComponentFactory.createCompositeNodeComponent();
                    node.setId(child.getId());
                    node.setName(child.getName());
                    node.setLft(child.getLft());
                    node.setRgt(child.getRgt());
                    node.setDepth(child.getDepth());
                }
                node.setParent(parent);
                parent.addSubNode(buildTreeRecursive(node, nodeList,i));
            }
            i++;
        }
        return parent;
    }

    public List<NodeComponent> getLeafList(NodeComponent node) {
        List<NodeComponent> leafNodes = new ArrayList<>();

        // Base case: If the node is null, return an empty list
        if (node == null) {
            return leafNodes;
        }

        if (node.getRgt() == node.getLft()+1) {
            leafNodes.add(node);
        } else {
            for (NodeComponent child : node.getChildren()) {
                leafNodes.addAll(getLeafList(child));
            }
        }
        return leafNodes;
    }
}
