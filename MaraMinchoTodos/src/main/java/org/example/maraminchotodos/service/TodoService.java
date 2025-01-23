package org.example.maraminchotodos.service;

import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.GetTodoResponse;
import org.example.maraminchotodos.dto.RemoveTodoRequest;
import org.example.maraminchotodos.dto.UpdateTodoRequest;
import org.springframework.stereotype.Service;

@Service
public interface TodoService {
    Boolean createTodo(CreateTodoRequest request);
    GetTodoResponse getTodos();
    Boolean updateTodo(UpdateTodoRequest request);
    Boolean removeTodo(RemoveTodoRequest request);
}
