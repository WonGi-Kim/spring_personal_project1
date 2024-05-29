package com.sparta.springpersonalproject1.todo.controller;

import com.sparta.springpersonalproject1.Enum.StatusEnum;
import com.sparta.springpersonalproject1.util.custom.CustomResponse;
import com.sparta.springpersonalproject1.todo.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.todo.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.todo.service.ToDoListService;
import com.sparta.springpersonalproject1.util.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private final ToDoListService toDoListService;
    private final JwtUtil jwtUtil;


    public ToDoController(ToDoListService toDoListService, JwtUtil jwtUtil) {
        this.toDoListService = toDoListService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/todo", produces = "application/json")
    public ResponseEntity<CustomResponse<?>> createToDo(@RequestHeader("Authorization") String token,@RequestBody ToDoRequestDto toDoRequestDto) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            ToDoResponseDto responseDto = toDoListService.createToDo(toDoRequestDto, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(responseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }


    @GetMapping("/todos")
    public ResponseEntity<CustomResponse<ToDoResponseDto>> getAllToDos() {
        List<ToDoResponseDto> responseDtos = toDoListService.getAllToDos();
        return ResponseEntity.ok(setHttpStatus(responseDtos));
    }


    @GetMapping("/todo/{id}")
    public ResponseEntity<CustomResponse<?>> getToDo(@PathVariable Long id) {

        ToDoResponseDto responseDto = toDoListService.getToDo(id);
        if (responseDto != null) {
            return ResponseEntity.ok().body(CustomResponse.makeResponse(responseDto, HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check To Do Id", HttpStatus.BAD_REQUEST));
        }
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<CustomResponse<?>> updateToDo(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ToDoRequestDto toDoRequestDto) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            ToDoResponseDto responseDto = toDoListService.updateToDo(id, toDoRequestDto, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(responseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/todo/{id}/{password}")
    public ResponseEntity<CustomResponse<?>> deleteToDo(@RequestHeader("Authorization") String token, @PathVariable Long id, @PathVariable String password) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            Long deletedNumber = toDoListService.deleteToDo(id, password, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(deletedNumber, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/todo/delete/")
    public ResponseEntity<CustomResponse<?>> deleteAllToDos(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(toDoListService.deleteAll(username), HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }

    private CustomResponse setHttpStatus(List<ToDoResponseDto> responseDtos) {
        int statusCode = StatusEnum.OK.getStatusCode();
        String message = StatusEnum.OK.getStatusMessage();

        CustomResponse<List<ToDoResponseDto>> customResponse = new CustomResponse<>();
        customResponse.setCode(statusCode);
        customResponse.setMessage(message);
        customResponse.setBody(responseDtos);
        return customResponse;
    }
}

