package com.sparta.springpersonalproject1.repository;

import com.sparta.springpersonalproject1.dto.ToDoResponseDto;
import com.sparta.springpersonalproject1.entity.ToDoList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public List<ToDoResponseDto> findAllToDos() {
        String sql = "SELECT * FROM todoTable ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::mapRowForToDo);
    }

    public ToDoResponseDto getToDo(Long id) {
        String sql = "SELECT * FROM todoTable WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowForToDo);
    }

    private ToDoResponseDto mapRowForToDo(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        String manager = resultSet.getString("manager");
        String password = resultSet.getString("password");
        String date = resultSet.getString("date");

        return new ToDoResponseDto(id, title, content, manager, password);
    }
}

