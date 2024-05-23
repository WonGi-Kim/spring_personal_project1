package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.CommentResponseDto;
import com.sparta.springpersonalproject1.service.CommentService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto);
        return commentResponseDto;
    }
}
