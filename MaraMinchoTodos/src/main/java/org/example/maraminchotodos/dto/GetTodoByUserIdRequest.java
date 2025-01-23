package org.example.maraminchotodos.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GetTodoByUserIdRequest {
    private Long userId;
}
