package org.example.maraminchotodos.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GetTodoByIdRequest {
    private final Long id;
}
