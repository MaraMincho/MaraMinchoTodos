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

    private String joinSQL(String[] target) {
        return String.join(" ", target);
    }

    private String makeStringField(String target) {
        return "'" + target + "'";
    }

    public String insertTodoSQL(Long userId, String title, String content) {
        String[] target = {
                userId.toString(),
                "true",
                makeStringField(title),
                makeStringField(content)
        };
        String valueString = String.join(",", target);
        String[] sqlTarget = {
                "INSERT INTO",
                getTableName(),
                "(user_id, is_show, title, content)",
                "VALUES",
                "(" + valueString + ")"
        };
        return joinSQL(sqlTarget);
    }

    public String archiveInsertTodoSQL(Long userId, String title, String content) {
        String [] target = {
                userId.toString(),
                makeStringField(title),
                makeStringField(content),
        };
        String valueString = String.join(",", target);
        String [] sqlTarget = {
                "INSERT INTO",
                getTableName(),
                "(user_id, is_show, title, content)",
                "VALUES",
                "(" + valueString + ")"
        };
        return joinSQL(sqlTarget);
    }

    public String deleteTodoSQL(Long id) {
        String[] sqlTarget = {
                "DELETE FROM",
                getTableName(),
                "WHERE id",
                id.toString()
        };
        return joinSQL(sqlTarget);
    }

    public String deleteAllTodosSQL() {
        String[] sqlTarget = {
                "DELETE FROM",
                getTableName()
        };
        return joinSQL(sqlTarget);
    }

    public String getAllTodosSQL() {
        String[] sqlTarget = {
                "SELECT *",
                "FROM",
                getTableName()
        };
        return joinSQL(sqlTarget);
    }

    public String getTodoByIdSQL(Long id) {
        String [] sqlTarget = {
                "SELECT *",
                "FROM",
                getTableName(),
                "WHERE id=",
                id.toString()
        };
        return joinSQL(sqlTarget);
    }

    public String getTodoByUserIdSQL(Long id) {
        String[] sqlTarget = {
                "SELECT * FROM",
                getTableName(),
                "WHERE user_id=",
                id.toString()
        };
        return joinSQL(sqlTarget);
    }

    public String updateTodoSQL(Long id, String title, String content) {
        var setString =  "(" + String.join(",", title, content) + ")";
        String[] sqlTarget = {
                "UPDATE",
                getTableName(),
                "SET(title, content) =",
                setString,
                "WHERE id == ",
                id.toString()
        };
        return joinSQL(sqlTarget);
    }
}
