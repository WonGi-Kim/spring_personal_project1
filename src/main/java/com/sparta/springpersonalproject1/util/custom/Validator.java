package com.sparta.springpersonalproject1.util.custom;

import com.sparta.springpersonalproject1.comment.entity.Comment;
import com.sparta.springpersonalproject1.todo.entity.ToDoList;
import com.sparta.springpersonalproject1.user.entity.User;

import java.util.Optional;

public class Validator {

    public static <T> T validateEntity(Optional<T> entity, String errorMessage) {
        return entity.orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    public static User validateUser(Optional<User> user) {
        return validateEntity(user, "Invalid User ID");
    }

    public static ToDoList validateToDoList(Optional<ToDoList> toDoList) {
        return validateEntity(toDoList, "선택한 일정이 존재하지 않습니다.");
    }

    public static Comment validateComment(Optional<Comment> comment) {
        return validateEntity(comment, "선택한 댓글이 존재하지 않습니다.");
    }
}

