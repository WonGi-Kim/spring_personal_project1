package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.service.ToDoListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/todos")
    public List<ToDoResponseDto> getAllToDos() {
        return toDoListService.getAllToDos();
    }

    @GetMapping("/todo/{id}")
    public ToDoResponseDto getToDo(@PathVariable Long id) {
        return toDoListService.getToDo(id);
    }

    @PutMapping("/todo/{id}/{password}")
    public Long updateToDo(@PathVariable Long id,  @PathVariable String password, @RequestBody ToDoRequestDto toDoRequestDto) {
        return toDoListService.updateToDo(id, password, toDoRequestDto);
    }
}

