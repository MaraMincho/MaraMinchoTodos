package org.example.maraminchotodos.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.domain.Todo;

import java.util.List;

@Getter
public class GetTodoResponse {
    private final List<Todo> todos;

    public GetTodoResponse(List<Todo> todos) {
        this.todos = todos;
    }
}
