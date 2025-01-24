package org.example.maraminchotodos.service.normal;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.GetTodoResponse;
import org.example.maraminchotodos.dto.RemoveTodoRequest;
import org.example.maraminchotodos.dto.UpdateTodoRequest;
import org.example.maraminchotodos.repository.ArchiveTodoRepository;
import org.example.maraminchotodos.repository.TodoDefaultRepository;
import org.example.maraminchotodos.service.TodoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NormalService implements TodoService {
    private final TodoDefaultRepository defaultRepository;
    private final ArchiveTodoRepository archiveTodoRepository;

    @Override
    public Boolean createTodo(CreateTodoRequest dto) {
        return defaultRepository.addTodo(dto);
    }

    @Override
    public GetTodoResponse getTodos() {
        return new GetTodoResponse(defaultRepository.getTodoByUserId(1L));
    }


    @Override
    public Boolean updateTodo(UpdateTodoRequest request) {
        var todo = defaultRepository.getTodo(request.id());
        if (todo.isEmpty()) {
            return false;
        }
        archiveTodoRepository.addTodo(todo.get());
        return defaultRepository.updateTodo(request);
    }

    @Override
    public Boolean removeTodo( RemoveTodoRequest request) {
        var todo = defaultRepository.getTodo(request.id());
        if (todo.isEmpty()) {
            return false;
        }
        archiveTodoRepository.addTodo(todo.get());
        return defaultRepository.removeTodo(request);
    }
}
