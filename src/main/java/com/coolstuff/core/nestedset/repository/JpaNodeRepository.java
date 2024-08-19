package com.coolstuff.core.nestedset.repository;

import com.coolstuff.core.nestedset.model.NodeComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaNodeRepository<T extends NodeComponent,ID> extends JpaRepository<T,ID> {
    List<T> findAllByOrderByLft();
}
