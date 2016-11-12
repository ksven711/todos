package com.sample.repository;

import com.sample.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vshan on 11/11/16.
 */
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
