package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.CommentResponseDto;
import com.sparta.springpersonalproject1.entity.Comment;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.repository.CommentRepository;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ToDoListRepository toDoListRepository;

    public CommentService(CommentRepository commentRepository, ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto addComment(CommentRequestDto commentRequestDto) {
        ToDoList toDoList = toDoListRepository.findById(commentRequestDto.getToDoListId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid ToDoList ID"));

        Comment comment = new Comment(commentRequestDto, toDoList);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

}