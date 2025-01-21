package org.example.maraminchotodos.service;

import org.springframework.stereotype.Service;

@Service
public interface TodoService {
    void createTodo(String title, String content);
}
