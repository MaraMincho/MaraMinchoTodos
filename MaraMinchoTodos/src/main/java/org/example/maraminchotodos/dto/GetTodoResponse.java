package org.example.maraminchotodos.dto;

import org.example.maraminchotodos.domain.Todo;

import java.util.List;

public record GetTodoResponse(List<Todo> todos) {
}
