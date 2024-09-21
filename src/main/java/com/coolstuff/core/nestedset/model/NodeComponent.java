package com.coolstuff.core.nestedset.model;

import java.util.Set;

public abstract class NodeComponent {
    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Integer getLft();

    public abstract void setLft(Integer lft);

    public abstract Integer getRgt();

    public abstract void setRgt(Integer rgt);

    public abstract Integer getDepth();

    public abstract void setDepth(Integer depth);

    public Set<NodeComponent> getChildren() {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    public void setChildren(Set<NodeComponent> children) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    public void addSubNode(NodeComponent child) {
        throw new UnsupportedOperationException();
    }

    public NodeComponent getParent() {
        throw new UnsupportedOperationException();
    }

    public void setParent(NodeComponent parent) {
        throw new UnsupportedOperationException();
    }

    public void print(String i) {
        throw new UnsupportedOperationException();
    }
}
