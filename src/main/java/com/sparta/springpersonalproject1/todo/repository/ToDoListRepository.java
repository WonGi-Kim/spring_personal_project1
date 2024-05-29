package com.sparta.springpersonalproject1.todo.repository;

import com.sparta.springpersonalproject1.todo.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
}

