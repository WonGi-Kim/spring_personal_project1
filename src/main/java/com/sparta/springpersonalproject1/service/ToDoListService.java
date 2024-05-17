package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;

    public ToDoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    public ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto) {
        ToDoList toDoList = new ToDoList(toDoRequestDto);
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
        ToDoList toDoList = toDoListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        return new ToDoResponseDto(toDoList);
    }

    public ToDoResponseDto updateToDo(Long id, ToDoRequestDto toDoRequestDto) {
        ToDoList toDoList = toDoListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        validatePassword(toDoList, toDoRequestDto);

        toDoList.updateFromDto(toDoRequestDto);  // 업데이트 메서드 호출
        ToDoList updatedToDoList = toDoListRepository.save(toDoList);
        return new ToDoResponseDto(updatedToDoList);
    }

    public Long deleteToDo(Long id, String inputPassword) {
        ToDoList toDoList = toDoListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        validatePassword(toDoList, inputPassword);
        toDoListRepository.deleteById(id);
        return toDoList.getId();
    }

    private void validatePassword(ToDoList toDoList, String inputPassword) {
        if (!inputPassword.equals(toDoList.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }
    private void validatePassword(ToDoList toDoList, ToDoRequestDto toDoRequestDto) {
        if (!toDoRequestDto.getPassword().equals(toDoList.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }
}