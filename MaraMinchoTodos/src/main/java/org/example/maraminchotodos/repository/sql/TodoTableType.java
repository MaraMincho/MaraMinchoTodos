package org.example.maraminchotodos.repository.sql;

public enum TodoTableType {
    ARCHIVED,
    NORMAL;

    public String getTableName() {
        return switch (this) {
            case ARCHIVED:
                yield "removed_todos";
            case NORMAL:
                yield "todos";
        };
    }

    public String insertTodoSQL(Long userId, String title, String content) {
        String[] target = {userId.toString(), title, content};
        String valueString = String.join(",", target);
        return "INSERT INTO" +
                getTableName() +
                "(userId, title, content)" +
                "values" +
                "(" + valueString + ")";
    }

    public String deleteTodoSQL(Long id) {
        return "DELETE FROM" + getTableName() + "WHERE id" + id.toString();
    }

    public String deleteAllTodosSQL() {
        return "DELETE FROM" + getTableName();
    }

    public String getAllTodosSQL() {
        return "SELECT * FROM" + getTableName();
    }

    public String getTodoByIdSQL(Long id) {
        return "SELECT * FROM" + getTableName() + "WHERE id=" + id.toString();
    }
    public String getTodoByUserIdSQL(Long id) {
        return "SELECT * FROM" + getTableName() + "WHERE userId=" + id.toString();
    }
    public String updateTodoSQL(Long id, String title, String content) {
        var setString =  "(" + String.join(",", title, content) + ")";
        return "UPDATE" + getTableName() +
                "SET(title, content) =" +
                setString +
                "WHERE id == " +
                id;
    }
}
