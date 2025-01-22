package org.example.maraminchotodos.service;

import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.GetTodoResponse;
import org.springframework.stereotype.Service;

@Service
public interface TodoService {
    void createTodo(CreateTodoRequest request);
    GetTodoResponse getTodos();

}
