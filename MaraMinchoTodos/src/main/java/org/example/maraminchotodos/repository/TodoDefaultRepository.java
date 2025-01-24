package org.example.maraminchotodos.repository;

import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.domain.util.TodoAndResultSetUtility;
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

    // Custom functional interface to remove try-catch from executor
    // For
    @FunctionalInterface
    interface PreparedStatementExecutor<T> {
        T execute(PreparedStatement prstmt) throws SQLException;
    }

    private <T> T createPreparedStatement(String sql, PreparedStatementExecutor<T> executor) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement prstmt = conn.prepareStatement(sql);
        ) {
            return executor.execute(prstmt);
        }
    }

    // CREATE
    public boolean addTodo(CreateTodoRequest dto) {
        String sql = tableType.insertTodoSQL(dto.userId(), dto.title(), dto.content());

        try {
            // Directly propagate SQLException
            return createPreparedStatement(sql, PreparedStatement::execute);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<Todo> getTodoByUserId(Long userId) {
        String sql = tableType.getTodoByUserIdSQL(userId);
        try  {
            return createPreparedStatement(sql, prstmt -> {
                ResultSet rs = prstmt.executeQuery();
                return TodoAndResultSetUtility.convertResultSeTodoList(rs);
            });

        } catch (SQLException e) {
            // Send new Array List;
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean updateTodo(UpdateTodoRequest dto) {
        String sql = tableType.updateTodoSQL(dto.id(), dto.title(), dto.content());
        try {
            return createPreparedStatement(sql, PreparedStatement::execute);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Todo> getTodo(Long id) {
        String sql = tableType.getTodoByIdSQL(id);
        try {
            return createPreparedStatement(sql, prstmt -> {
                var result = prstmt.executeQuery();
                if (!result.next()) {
                    return Optional.empty();
                }
                return Optional.of(TodoAndResultSetUtility.convertResultSetTodo(result));
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean removeTodo(RemoveTodoRequest dto) {
        String sql = tableType.deleteTodoSQL(dto.id());
        try  {
            return createPreparedStatement(sql, PreparedStatement::execute);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hideTodo(RemoveTodoRequest dto) {
        String sql = tableType.hideTodoSQL(dto.id());
        try {
            return createPreparedStatement(sql, PreparedStatement::execute);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAll() {
        String sql = tableType.deleteAllTodosSQL();
        try  {
            return createPreparedStatement(sql, PreparedStatement::execute);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
