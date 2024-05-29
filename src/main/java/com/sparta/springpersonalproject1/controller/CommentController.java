package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.commentDto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.commentDto.CommentResponseDto;
import com.sparta.springpersonalproject1.dto.CustomResponse;
import com.sparta.springpersonalproject1.service.CommentService;
import com.sparta.springpersonalproject1.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sparta.springpersonalproject1.dto.CustomResponse.makeResponse;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/comment", produces = "application/json")
    public ResponseEntity<CustomResponse<?>> addComment(@RequestHeader("Authorization") String token,
                                                        @RequestBody CommentRequestDto commentRequestDto) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            CommentResponseDto commentResponseDto = commentService.addComment(commentRequestDto, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(commentResponseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check ToDoListId or commentDto Content does not null", HttpStatus.BAD_REQUEST));
        }
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CustomResponse<?>> updateComment(@RequestHeader("Authorization") String token,
                                                           @PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(commentResponseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check CommentContent or username", HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CustomResponse<?>> deleteComment(@RequestHeader("Authorization") String token,
                                                           @PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            String message = commentService.deleteComment(id, commentRequestDto, username);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(message, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CustomResponse.makeResponse("Invalid input : Check username or existence comment", HttpStatus.BAD_REQUEST));
        }
    }

}