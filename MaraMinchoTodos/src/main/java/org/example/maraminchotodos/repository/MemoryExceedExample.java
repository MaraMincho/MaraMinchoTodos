package org.example.maraminchotodos.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class MemoryExceedExample {
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public static void main(String[] args) {
        try {
            // H2 메모리 데이터베이스에 연결
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // 메모리를 많이 사용하도록 수많은 데이터를 삽입
            for (int i = 1; i <= 1_000_000; i++) {
                insertData(connection, i, "User" + i);
            }

            System.out.println("데이터 삽입 완료.");
            // Connection을 닫지 않음
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) {
        try {
            String createTableSql = "CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(50))";
            PreparedStatement preparedStatement = connection.prepareStatement(createTableSql);
            preparedStatement.executeUpdate();
            // PreparedStatement를 닫지 않음
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertData(Connection connection, int id, String name) {
        try {
            String insertSql = "INSERT INTO todos(user_id, is_show, title, content) VALUES (1,true, 'title1', 'content1');";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.executeUpdate();
            // PreparedStatement를 닫지 않음
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
