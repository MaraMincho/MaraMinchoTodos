package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.RemoveTodoRequest;
import org.example.maraminchotodos.dto.UpdateTodoRequest;
import org.example.maraminchotodos.repository.sql.TodoTableType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoDefaultRepository {
    // DB 연결 정보 TODO 수정
    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    private TodoTableType tableType = TodoTableType.NORMAL;
    // CREATE
    public boolean addTodo(CreateTodoRequest dto) {
        String sql = tableType.insertTodoSQL(dto.getUserId(), dto.getTitle(), dto.getContent());

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            return pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // READ
    public List<Todo> getTodoById(Long userId) {
        String sql = tableType.getTodoByUserIdSQL(userId);
        List<Todo> todos = new ArrayList<>();
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                todos.add(convertResultSetTodo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public boolean updateTodo(UpdateTodoRequest dto) {
        String sql = tableType.updateTodoSQL(dto.getId(), dto.getTitle(), dto.getContent());
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            return pstmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Todo> getTodo(Long id) {
        String sql = tableType.getTodoByIdSQL(id);
        try(
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            var result = pstmt.executeQuery();
            if (!result.next()) {
                return Optional.empty();
            }
            return Optional.of(convertResultSetTodo(result));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean removeTodo(RemoveTodoRequest dto) {
        String sql = tableType.deleteTodoSQL(dto.getId());
        try(
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement deletePS = conn.prepareStatement(sql);
        ) {
            return deletePS.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAll() {
        String sql = tableType.deleteAllTodosSQL();
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            return pstmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            return false
        }
    }

    private Todo convertResultSetTodo(ResultSet rs) throws SQLException {
        return new Todo(
                rs.getLong("id"),      // Ensure column names match table definition
                rs.getLong("userId"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
