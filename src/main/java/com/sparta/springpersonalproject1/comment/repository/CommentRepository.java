package com.sparta.springpersonalproject1.comment.repository;

import com.sparta.springpersonalproject1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
