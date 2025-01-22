package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.RemoveTodoRequest;
import org.example.maraminchotodos.dto.UpdateTodoRequest;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean updateTodo(UpdateTodoRequest dto) {
        var title = dto.getTitle();
        var content = dto.getContent();
        var id = dto.getId();
        String sql = """
                    UPDATE todos 
                    SET(title, content) = (?, ?)
                    WHERE id == (?)
                    """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setLong(3, id);

            //TODO: - 에러 처리 로직 새성
            pstmt.execute();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Todo> getTodo(Long id) {
        String sql = """
                SELECT * FROM todos WHERE id = ?
                """;

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
        String sql = """
                DELTE FROM todos
                where id = ?
                """;
        String findTargetTodoSQL = "SELECT * FROM todos WHERE id = ?";
        String insertSQL = "INSERT INTO removed_todos(userId, title, content) VALUES(?, ?, ?)";
        try(
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement findPs = conn.prepareStatement(findTargetTodoSQL);
                PreparedStatement deletePS = conn.prepareStatement(sql);
                PreparedStatement insertPS = conn.prepareStatement(insertSQL);

        ) {
            var findResult = findPs.executeQuery();
            // Query조회 실패시 return
            if (!findResult.next()) {
                return false;
            }
            final var currentTodo = convertResultSetTodo(findResult);


            deletePS.setLong(1, dto.getId());

            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
