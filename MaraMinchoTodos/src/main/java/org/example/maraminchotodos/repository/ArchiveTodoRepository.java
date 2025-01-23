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

    private final TodoTableType tableType = TodoTableType.ARCHIVED;

    private PreparedStatement createPreparedStatement(String sql) throws SQLException{
        final Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return conn.prepareStatement(sql);
    }

    public boolean addTodo(Todo todo) {
        String sql = tableType.insertTodoSQL(todo.getUserId(), todo.getTitle(), todo.getContent());
        try (final PreparedStatement pstmt = createPreparedStatement(sql)) {
            return pstmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Todo> getTodoByUserId(Long userId) {
        String sql = tableType.getTodoByUserIdSQL(userId);
        List<Todo> todos = new ArrayList<>();

        try( PreparedStatement pstmt = createPreparedStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            todos = TodoAndResultSetUtility.convertResultSeTodoList(rs);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public List<Todo> getTodoByOriginalId(GetTodoByIdRequest request) {
        String sql = "SELECT * FROM" + tableType.getTableName() + "WHERE originalId" + request.id().toString();
        List<Todo> todos = new ArrayList<>();

        try(PreparedStatement pstmt = createPreparedStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            todos = TodoAndResultSetUtility.convertResultSeTodoList(rs);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }
}
