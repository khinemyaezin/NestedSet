package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractTreeBuilderTest {
    NodeComponentFactory factory;
    AbstractTreeBuilder treeBuilder;
    @Getter
    @Setter
    static
    class Node extends NodeComponent {
        Long id;
        String name;
        Integer lft;
        Integer rgt;
        Integer depth;
        NodeComponent parent;
        Set<NodeComponent> children = new HashSet<>();

        @Override
        public void addSubNode(NodeComponent child) {
            children.add(child);
        }
    }

    static class TreeBuilderImpl extends AbstractTreeBuilder{

        public TreeBuilderImpl(NodeComponentFactory nodeComponentFactory) {
            super(nodeComponentFactory);
        }

        @Override
        public void merge(NodeComponent source, NodeComponent target) {
            target.setLft(source.getLft());
            target.setRgt(source.getRgt());
            target.setDepth(source.getDepth());
        }
    }

    @BeforeEach
    void init() {
        factory = mock(NodeComponentFactory.class);
        when(factory.createCompositeNodeComponent()).thenAnswer( o-> new Node());
        when(factory.createLeafNodeComponent()).thenAnswer( o-> new Node());

        treeBuilder = new TreeBuilderImpl(factory);
    }

    @Test
    void shouldReturnTwoLeaves_whenInputParent() {
        NodeComponent root = mock(NodeComponent.class);
        NodeComponent electronic = mock(NodeComponent.class);
        NodeComponent computer = mock(NodeComponent.class);
        NodeComponent mobile = mock(NodeComponent.class);

        when(root.getLft()).thenReturn(1);
        when(root.getRgt()).thenReturn(8);
        when(root.getDepth()).thenReturn(0);

        when(electronic.getLft()).thenReturn(2);
        when(electronic.getRgt()).thenReturn(7);
        when(electronic.getDepth()).thenReturn(1);

        when(computer.getLft()).thenReturn(3);
        when(computer.getRgt()).thenReturn(4);
        when(computer.getDepth()).thenReturn(2);

        when(mobile.getLft()).thenReturn(5);
        when(mobile.getRgt()).thenReturn(6);
        when(mobile.getDepth()).thenReturn(2);

        when(root.getChildren()).thenReturn(Set.of(electronic));
        when(electronic.getParent()).thenReturn(root);
        when(computer.getParent()).thenReturn(electronic);
        when(mobile.getParent()).thenReturn(electronic);
        when(electronic.getChildren()).thenReturn(Set.of(computer,mobile));

        List<NodeComponent> result = treeBuilder.getLeafList(root);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(computer));
        Assertions.assertTrue(result.contains(mobile));

    }

    @Test
    void shouldReturnOneParentAndTwoChildren_whenInputNodeList() {
        NodeComponent root = mock(NodeComponent.class);
        NodeComponent computer = mock(NodeComponent.class);
        NodeComponent mobile = mock(NodeComponent.class);

        when(root.getLft()).thenReturn(1);
        when(root.getRgt()).thenReturn(6);
        when(root.getDepth()).thenReturn(0);

        when(computer.getLft()).thenReturn(2);
        when(computer.getRgt()).thenReturn(3);
        when(computer.getDepth()).thenReturn(1);

        when(mobile.getLft()).thenReturn(4);
        when(mobile.getRgt()).thenReturn(5);
        when(mobile.getDepth()).thenReturn(1);

        var result = treeBuilder.buildTree(List.of(root,computer,mobile));

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getLft(), 1);
        Assertions.assertEquals(2, result.get().getChildren().size());
    }

    @Test
    void shouldReturnFirstParent_whenInputMultipleParents() {
        NodeComponent r1 = mock(NodeComponent.class);
        NodeComponent r1_1 = mock(NodeComponent.class);
        NodeComponent r2 = mock(NodeComponent.class);
        NodeComponent r2_1 = mock(NodeComponent.class);

        when(r1.getLft()).thenReturn(1);
        when(r1.getRgt()).thenReturn(4);
        when(r1.getDepth()).thenReturn(0);

        when(r1_1.getLft()).thenReturn(2);
        when(r1_1.getRgt()).thenReturn(3);
        when(r1_1.getDepth()).thenReturn(1);

        when(r2.getLft()).thenReturn(5);
        when(r2.getRgt()).thenReturn(8);
        when(r2.getDepth()).thenReturn(0);

        when(r2_1.getLft()).thenReturn(6);
        when(r2_1.getRgt()).thenReturn(7);
        when(r2_1.getDepth()).thenReturn(1);

        var result = treeBuilder.buildTree(List.of(r1, r1_1, r2, r2_1));

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getLft(), 1);
        Assertions.assertEquals(1, result.get().getChildren().size());
    }
}