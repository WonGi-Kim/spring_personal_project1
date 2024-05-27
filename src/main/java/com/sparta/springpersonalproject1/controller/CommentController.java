package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.commentDto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.commentDto.CommentResponseDto;
import com.sparta.springpersonalproject1.dto.CustomResponse;
import com.sparta.springpersonalproject1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment", produces = "application/json")
    public ResponseEntity<CustomResponse<?>> addComment(@RequestBody CommentRequestDto commentRequestDto) {
        try {
            CommentResponseDto commentResponseDto = commentService.addComment(commentRequestDto);
            return ResponseEntity.ok().body(makeResponse(commentResponseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CustomResponse<?>> updateComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto);
            return ResponseEntity.ok().body(makeResponse(commentResponseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(makeResponse("Invalid input : Check CommentContent or username", HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CustomResponse<?>> deleteComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            String message = commentService.deleteComment(id, commentRequestDto);
            return ResponseEntity.ok().body(makeResponse(message, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(makeResponse("Invalid input : Check username or existence comment", HttpStatus.BAD_REQUEST));
        }
    }

    private <T> CustomResponse<T> makeResponse(T responseBody, HttpStatus status) {
        CustomResponse<T> customResponse = new CustomResponse<>();
        customResponse.setCode(status.value());
        customResponse.setMessage(status.getReasonPhrase());
        customResponse.setBody(responseBody);

        return customResponse;
    }

}