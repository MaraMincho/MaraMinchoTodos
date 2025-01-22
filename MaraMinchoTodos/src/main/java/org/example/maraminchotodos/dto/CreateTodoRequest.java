package org.example.maraminchotodos.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class CreateTodoRequest {
    private final Long userId;
    private final String title;
    private final String content;
}