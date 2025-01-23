package org.example.maraminchotodos.dto;

public record CreateTodoRequest(Long userId, String title, String content) {
}