package com.coolstuff.core.nestedset.service;

import com.coolstuff.core.nestedset.model.NodeComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TreeBuilderImplTest {

    @Test
    void reverseTree() {
        NodeComponent root = mock(NodeComponent.class);
        NodeComponent electronic = mock(NodeComponent.class);
        NodeComponent computer = mock(NodeComponent.class);
        NodeComponent mobile = mock(NodeComponent.class);

        when(root.getName()).thenReturn("Root");
        when(root.getLft()).thenReturn(1);
        when(root.getRgt()).thenReturn(8);
        when(root.getDepth()).thenReturn(0);

        when(electronic.getName()).thenReturn("Electronic");
        when(electronic.getLft()).thenReturn(2);
        when(electronic.getRgt()).thenReturn(7);
        when(electronic.getDepth()).thenReturn(1);

        when(computer.getName()).thenReturn("Computer");
        when(computer.getLft()).thenReturn(3);
        when(computer.getRgt()).thenReturn(4);
        when(computer.getDepth()).thenReturn(2);

        when(mobile.getName()).thenReturn("Mobile");
        when(mobile.getLft()).thenReturn(5);
        when(mobile.getRgt()).thenReturn(6);
        when(mobile.getDepth()).thenReturn(2);

        when(root.getChildren()).thenReturn(Set.of(electronic));
        when(electronic.getParent()).thenReturn(root);
        when(computer.getParent()).thenReturn(electronic);
        when(mobile.getParent()).thenReturn(electronic);
        when(electronic.getChildren()).thenReturn(Set.of(computer,mobile));

        NodeComponentFactory factory = mock(NodeComponentFactory.class);
        TreeBuilderImpl treeBuilder = new TreeBuilderImpl(factory);

        List<NodeComponent> result = treeBuilder.getLeafList(root);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(computer));
        Assertions.assertTrue(result.contains(mobile));

    }
}