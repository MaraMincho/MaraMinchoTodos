package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.repository.sql.TodoTableType;
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

    private final TodoTableType tableType = TodoTableType.ARCHIVED;

    private PreparedStatement createPreparedStatement(String sql) throws SQLException{
        final Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return conn.prepareStatement(sql);
    }

    public boolean addTodo(Todo todo) {
        String sql =
    }
}
