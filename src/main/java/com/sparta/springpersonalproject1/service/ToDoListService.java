package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.CustomResponse;
import com.sparta.springpersonalproject1.dto.todoDto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.todoDto.ToDoResponseDto;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.entity.User;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import com.sparta.springpersonalproject1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;
    private final UserRepository userRepository;

    public ToDoListService(ToDoListRepository toDoListRepository,
                           UserRepository userRepository) {
        this.toDoListRepository = toDoListRepository;
        this.userRepository = userRepository;
    }

    public ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto, String username) {
        User user = findByUsername(username);

        ToDoList toDoList = new ToDoList(toDoRequestDto, user);

        user.getToDoLists().add(toDoList);
        toDoList.setDate(new Timestamp(System.currentTimeMillis()));
        toDoList.setUsername(username);
        ToDoList savedToDoList = toDoListRepository.save(toDoList);

        return new ToDoResponseDto(savedToDoList, true);
    }

    public List<ToDoResponseDto> getAllToDos() {
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return toDoLists.stream()
                .map(ToDoResponseDto::new)
                .collect(Collectors.toList());
    }

    public ToDoResponseDto getToDo(Long id) {
        ToDoList toDoList = findByToDoId(id);
        return new ToDoResponseDto(toDoList);
    }

    public ToDoResponseDto updateToDo(Long id, ToDoRequestDto toDoRequestDto, String username) {
        User user = findByUsername(username);
        ToDoList toDoList = findByToDoId(id);

        if(toDoList.getUsername().equals(user.getUsername())){
            toDoList.validatePassword(toDoRequestDto); // 패스워드 검사
            toDoList.updateFromDto(toDoRequestDto);  // 업데이트 메서드 호출
            ToDoList updatedToDoList = toDoListRepository.save(toDoList);
            return new ToDoResponseDto(updatedToDoList);
        } else {
            return null;
        }
    }

    public Long deleteToDo(Long id, String inputPassword, String username) {
        User user = findByUsername(username);
        ToDoList toDoList = findByToDoId(id);
        if(toDoList.getUsername().equals(user.getUsername())){
            toDoList.validatePassword(inputPassword);
            toDoListRepository.deleteById(id);
            return toDoList.getId();
        } else {
            return null;
        }
    }

    public String deleteAll(String username) {
        User user = findByUsername(username);
        if(user.getRole().equals("ADMIN")) {
            toDoListRepository.deleteAll();
            return "전부 삭제";
        } else {
            throw new IllegalArgumentException("User's role is not ADMIN");
        }
    }

    private ToDoList findByToDoId(Long id) {
        ToDoList toDoList = toDoListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        return toDoList;
    }

    private User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        return user;
    }


}