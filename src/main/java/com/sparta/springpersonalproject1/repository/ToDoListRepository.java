package com.sparta.springpersonalproject1.repository;

import com.sparta.springpersonalproject1.entity.ToDoList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ToDoListRepository {
    private final JdbcTemplate jdbcTemplate;

    public ToDoListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ToDoList saveToDoList(ToDoList toDoList) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO todoTable (title, content, manager, password, date) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, toDoList.getTitle());
            preparedStatement.setString(2, toDoList.getContent());
            preparedStatement.setString(3, toDoList.getManager());
            preparedStatement.setString(4, toDoList.getPassword());
            preparedStatement.setString(5, toDoList.getDate());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        toDoList.setId(id);

        return toDoList;
    }
}

