package org.example.maraminchotodos.domain.util;

import org.example.maraminchotodos.domain.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoAndResultSetUtility {
    public static Todo convertResultSetTodo(ResultSet rs) throws SQLException {
        return new Todo(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("content")
        );
    }

    public static List<Todo> convertResultSeTodoList(ResultSet rs) throws SQLException {
        List<Todo> todos = new ArrayList<>();
        while (rs.next()) {
            todos.add(convertResultSetTodo(rs));
        }
        return todos;
    }
}
