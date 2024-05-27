package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.commentDto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.commentDto.CommentResponseDto;
import com.sparta.springpersonalproject1.entity.Comment;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.repository.CommentRepository;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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

        // 데이터 유효성 검사
        if (commentRequestDto.getCommentContent() == null || commentRequestDto.getCommentContent().isEmpty()) {
            throw new IllegalArgumentException("Invalid commentDto Content");
        }

        Comment comment = new Comment(commentRequestDto, toDoList);
        Comment savedComment = commentRepository.save(comment);
        toDoList.getComments().add(comment);
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = findByCommentId(id);
        if (comment.getCommentContent() == null || comment.getCommentContent().isEmpty() ||
                !comment.getUsername().equals(commentRequestDto.getUsername()) ) {
            throw new IllegalArgumentException("Invalid commentDto Username");
        }

        comment.updateComment(commentRequestDto);
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment);
    }

    public String deleteComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = findByCommentId(id);
        if(comment.getUsername().equals(commentRequestDto.getUsername())) {
            commentRepository.deleteById(id);
            return "삭제 완료";
        } else {
            throw new IllegalArgumentException("Invalid commentDto Username");
        }
    }


    private Comment findByCommentId(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        return comment;
    }
}
