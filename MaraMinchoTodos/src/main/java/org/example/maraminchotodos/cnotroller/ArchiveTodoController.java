package org.example.maraminchotodos.cnotroller;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.repository.ArchiveTodoRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArchiveTodoController {
    private final ArchiveTodoRepository archiveTodoRepository;

    public List<Todo> getTodo
}
