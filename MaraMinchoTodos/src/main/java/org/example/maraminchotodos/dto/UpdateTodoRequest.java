package org.example.maraminchotodos.dto;

import java.util.Optional;

public record UpdateTodoRequest(Long id, Optional<String> title, Optional<String> content ) {

}
