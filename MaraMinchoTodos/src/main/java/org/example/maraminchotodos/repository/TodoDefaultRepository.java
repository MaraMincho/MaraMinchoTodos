package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoDefaultRepository {
    // DB 연결 정보 TODO 수정
    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    // CREATE
    public void addTodo(CreateTodoRequest dto) {
        String sql = "INSERT INTO todos (userId, title, content) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // TODO: - 하드 코딩 수정
            pstmt.setInt(1, 1);
            pstmt.setString(2, dto.getTitle());
            pstmt.setString(3, dto.getContent());
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // READ
    public List<Todo> getTodoById(int userId) {
        String sql = "SELECT * FROM todos WHERE userId = ?";
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                todos.add(convertResultSetTodo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void removeAll() {
        String sql = "DELETE FROM todos";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            //Method is only allowed for a query. Use execute or executeUpdate instead of executeQuery; SQL statement:
            pstmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
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
