package com.sparta.springpersonalproject1.repository;

import com.sparta.springpersonalproject1.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
}

