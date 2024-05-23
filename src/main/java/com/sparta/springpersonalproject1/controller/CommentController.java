package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.CommentResponseDto;
import com.sparta.springpersonalproject1.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment", produces = "application/json")
    public CommentResponseDto addComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.addComment(commentRequestDto);
        return commentResponseDto;
    }
}
