package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.StatusEnum;
import com.sparta.springpersonalproject1.dto.CustomResponse;
import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.service.ToDoListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private final ToDoListService toDoListService;

    public ToDoController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping(value = "/todo", produces = "application/json")
    public ResponseEntity<CustomResponse<ToDoResponseDto>> createToDo(@RequestBody ToDoRequestDto toDoRequestDto) {
        ToDoResponseDto responseDto = toDoListService.createToDo(toDoRequestDto);
        return ResponseEntity.ok(setHttpStatus(responseDto));
    }


    @GetMapping("/todos")
    public ResponseEntity<List<CustomResponse<ToDoResponseDto>>> getAllToDos() {
        List<ToDoResponseDto> responseDtos = toDoListService.getAllToDos();
        List<CustomResponse<ToDoResponseDto>> customResponses = new ArrayList<>();

        for (ToDoResponseDto responseDto : responseDtos) {
            customResponses.add(setHttpStatus(responseDto));
        }
        return ResponseEntity.ok(customResponses);
    }


    @GetMapping("/todo/{id}")
    public ResponseEntity<CustomResponse<ToDoResponseDto>> getToDo(@PathVariable Long id) {

        ToDoResponseDto responseDto = toDoListService.getToDo(id);
        if (responseDto != null) {
            return ResponseEntity.ok(setHttpStatus(responseDto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<CustomResponse<ToDoResponseDto>> updateToDo(@PathVariable Long id, @RequestBody ToDoRequestDto toDoRequestDto) {
        ToDoResponseDto responseDto = toDoListService.updateToDo(id, toDoRequestDto);
        return ResponseEntity.ok(setHttpStatus(responseDto));
    }

    @DeleteMapping("/todo/{id}/{password}")
    public Long deleteToDo(@PathVariable Long id, @PathVariable String password) {
        return toDoListService.deleteToDo(id, password);
    }

    private CustomResponse setHttpStatus(ToDoResponseDto responseDto) {
        int statusCode = StatusEnum.OK.getStatusCode();
        String message = StatusEnum.OK.getStatusMessage();

        CustomResponse<ToDoResponseDto> customResponse = new CustomResponse<>();
        customResponse.setCode(statusCode);
        customResponse.setMessage(message);
        customResponse.setBody(responseDto);
        return customResponse;
    }
}

