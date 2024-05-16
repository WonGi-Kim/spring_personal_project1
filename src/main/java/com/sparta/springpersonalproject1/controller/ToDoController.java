package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.service.ToDoListService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private final ToDoListService toDoListService;

    public ToDoController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping("/todo")
    public ToDoResponseDto createToDo(@RequestBody ToDoRequestDto toDoRequestDto) {
        return toDoListService.createToDo(toDoRequestDto);
    }

}

