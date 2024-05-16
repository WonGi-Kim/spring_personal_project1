package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;

    public ToDoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    public ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto) {
        ToDoList toDoList = new ToDoList(toDoRequestDto);
        ToDoList savedToDoList = toDoListRepository.saveToDoList(toDoList);
        ToDoResponseDto toDoResponseDto = new ToDoResponseDto(toDoList);
        return toDoResponseDto;
    }

    public List<ToDoResponseDto> getAllToDos() {
        return toDoListRepository.findAllToDos();
    }

    public ToDoResponseDto getToDo(Long id) {
        return toDoListRepository.getToDo(id);
    }
}

