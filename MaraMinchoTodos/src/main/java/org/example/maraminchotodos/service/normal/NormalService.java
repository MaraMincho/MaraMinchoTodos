package org.example.maraminchotodos.service.normal;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.GetTodoResponse;
import org.example.maraminchotodos.dto.UpdateTodoResponse;
import org.example.maraminchotodos.repository.TodoDefaultRepository;
import org.example.maraminchotodos.service.TodoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NormalService implements TodoService {
    private final TodoDefaultRepository repository;

    @Override
    public void createTodo(CreateTodoRequest dto) {
        repository.addTodo(dto);
    }

    @Override
    public GetTodoResponse getTodos() {
        return new GetTodoResponse(repository.getTodoByUserId(1L));
    }

    @Override
    public void updateTodo(UpdateTodoResponse request) {

    }


}
