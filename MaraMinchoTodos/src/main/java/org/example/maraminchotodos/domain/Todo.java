package org.example.maraminchotodos.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Todo {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
}
