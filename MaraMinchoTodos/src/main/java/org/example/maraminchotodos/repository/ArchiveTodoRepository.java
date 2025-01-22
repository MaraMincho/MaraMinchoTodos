package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ArchiveTodoRepository {
    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

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
}
