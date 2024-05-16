package com.sparta.springpersonalproject1.repository;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
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

    // 일정 저장
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
    // 목록 조회
    public List<ToDoResponseDto> findAllToDos() {
        String sql = "SELECT * FROM todoTable ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::mapRowForToDo);
    }

    // 일정 한 개 조회
    public ToDoResponseDto getToDo(Long id) {
        String sql = "SELECT * FROM todoTable WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowForToDo);
    }

    // 일정 수정
    public void updateToDo(Long id, ToDoRequestDto toDoRequestDto) {
        String sql = "UPDATE todoTable SET title = ?, content = ?, manager = ? WHERE id = ?";
        jdbcTemplate.update(sql,toDoRequestDto.getTitle(),toDoRequestDto.getContent(),toDoRequestDto.getManager(),id);
    }

    // 일정 조회 메서드
    private ToDoResponseDto mapRowForToDo(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        String manager = resultSet.getString("manager");
        String password = resultSet.getString("password");
        String date = resultSet.getString("date");

        return new ToDoResponseDto(id, title, content, manager, date);
    }

    public ToDoList findById(Long id) {
        String sql = "SELECT * FROM todoTable WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                ToDoList toDoList = new ToDoList();
                toDoList.setTitle(toDoList.getTitle());
                toDoList.setContent(toDoList.getContent());
                toDoList.setManager(toDoList.getManager());
                toDoList.setPassword(resultSet.getString("password"));
                toDoList.setDate(toDoList.getDate());
                return toDoList;
            } else {
                return null;
            }
        }, id);
    }

    public void deleteToDo(Long id) {
        String sql = "DELETE FROM todoTable WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

