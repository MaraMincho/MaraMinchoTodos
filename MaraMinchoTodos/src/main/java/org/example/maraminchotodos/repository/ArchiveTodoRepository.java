package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.domain.util.TodoAndResultSetUtility;
import org.example.maraminchotodos.dto.GetTodoByIdRequest;
import org.example.maraminchotodos.repository.sql.TodoTableType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArchiveTodoRepository {
    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    private static final TodoTableType tableType = TodoTableType.ARCHIVED;

    @FunctionalInterface
    private interface PreparedStatementExecutor<T> {
        T execute(PreparedStatement pstmt) throws SQLException;
    }

    private <T> T createPreparedStatement(String sql, PreparedStatementExecutor<T> executor) throws SQLException {
        try (
                final Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            return executor.execute(pstmt);
        }
    }

    public boolean addTodo(Todo todo) {
        String sql = tableType.archiveInsertTodoSQL(todo.getId(), todo.getUserId(), todo.getTitle(), todo.getContent());
        try {
            return createPreparedStatement(sql, PreparedStatement::execute);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Todo> getTodoByUserId(Long userId) {
        String sql = tableType.getTodoByUserIdSQL(userId);

        try {
            return createPreparedStatement(sql, pstmt -> {
                ResultSet rs = pstmt.executeQuery();
                return TodoAndResultSetUtility.convertResultSeTodoList(rs);
            });
        }catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Todo> getTodoByOriginalId(GetTodoByIdRequest request) {
        String sql = "SELECT * FROM " + tableType.getTableName() + " WHERE original_Id = " + request.id().toString();

        try {
            return createPreparedStatement(sql, pstmt -> {
                var rs = pstmt.executeQuery();
                return TodoAndResultSetUtility.convertResultSeTodoList(rs);
            });
        }catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Todo> getAllTodos() {
        String sql = tableType.getAllTodosSQL();
        try {
            return createPreparedStatement(sql, pstmt -> {
                var rs = pstmt.executeQuery();
                return TodoAndResultSetUtility.convertResultSeTodoList(rs);
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
