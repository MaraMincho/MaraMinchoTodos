package org.example.maraminchotodos.cnotroller;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.GetTodoByIdRequest;
import org.example.maraminchotodos.dto.GetTodoByUserIdRequest;
import org.example.maraminchotodos.repository.ArchiveTodoRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArchiveTodoController {
    private final ArchiveTodoRepository archiveTodoRepository;

    public List<Todo> getTodoById(GetTodoByIdRequest request) {
        return archiveTodoRepository.getTodoByOriginalId(request);
    }

    public List<Todo> getTodoByUserId(GetTodoByUserIdRequest request) {
        return archiveTodoRepository.getTodoByUserId(request.getUserId());
    }
}
