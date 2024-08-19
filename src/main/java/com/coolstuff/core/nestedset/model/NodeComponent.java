package com.coolstuff.core.nestedset.model;

import java.util.HashSet;
import java.util.Set;

public abstract class NodeComponent {
    public NodeComponent(){};
    public NodeComponent(Long id, String name, Integer lft, Integer rgt, Integer depth){
        setId(id);
        setName(name);
        setLft(lft);
        setRgt(rgt);
        setDepth(depth);
    }

    public abstract Long getId();

    public abstract String getName();

    public abstract Integer getLft();

    public abstract Integer getRgt();

    public abstract Integer getDepth();

    public Set<NodeComponent> getChildren(){
        throw new UnsupportedOperationException("Method is not implemented");
    }

    public void addSubNode(NodeComponent child){
        throw new UnsupportedOperationException();
    }

    public NodeComponent getParent(){
        throw new UnsupportedOperationException();
    }


    public abstract void setId(Long id);

    public abstract void setName(String name);

    public abstract void setLft(Integer lft);

    public abstract void setRgt(Integer rgt);

    public abstract void setDepth(Integer depth);

    public void setParent(NodeComponent parent){
        throw new UnsupportedOperationException();
    }

    public void setChildren(Set<NodeComponent> children) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    public void print(String i){
        throw new UnsupportedOperationException();
    }
}
