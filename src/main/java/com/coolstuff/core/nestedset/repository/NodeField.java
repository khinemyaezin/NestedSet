package com.coolstuff.core.nestedset.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeField {
    private String idFieldName;
    private String nameFieldName;
    private String leftFieldName;
    private String rightFieldName;
    private String depthFieldName;
}
