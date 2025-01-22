package org.example.maraminchotodos.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateTodoRequest {
    final String title;
    final String content;
    final Long id;
}
