package org.example.maraminchotodos.repository.sql;

public enum TodoTableType {
    ARCHIVED,
    NORMAL;

    public String getTableName() {
        return switch (this) {
            case ARCHIVED:
                yield "archived_todos";
            case NORMAL:
                yield "todos";
        };
    }

    private String joinSQL(String[] target) {
        return String.join(" ", target);
    }

    private String makeStringField(String target) {
        if (target == null) {
            return " null ";
        }
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

    public String archiveInsertTodoSQL(Long originalId, Long userId, String title, String content) {
        String [] target = {
                originalId.toString(),
                userId.toString(),
                makeStringField(title),
                makeStringField(content),
        };
        String valueString = String.join(",", target);
        String [] sqlTarget = {
                "INSERT INTO",
                getTableName(),
                "(original_id, user_id, title, content)",
                "VALUES",
                "(" + valueString + ")"
        };
        return joinSQL(sqlTarget);
    }

    public String hideTodoSQL(Long id) {
        String[] sqlTarget = {
                "UPDATE",
                getTableName(),
                "SET",
                "is_show = false",
                "WHERE",
                "id <>",
                id.toString()
        };
        return joinSQL(sqlTarget);
    }

    public String deleteTodoSQL(Long id) {
        String[] sqlTarget = {
                "DELETE FROM",
                getTableName(),
                "WHERE id =",
                id.toString(),
                "AND is_show=false",
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
        String[] sqlTarget = {
                "UPDATE",
                getTableName(),
                "SET",
                "title = NVL(", makeStringField(title), ", title), ",
                "content = NVL(", makeStringField(content), ", content)",
                "WHERE id = ", id.toString(),
        };
        return joinSQL(sqlTarget);
    }


}
