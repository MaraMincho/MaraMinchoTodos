package org.example.maraminchotodos.cnotroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.GetTodoByIdRequest;
import org.example.maraminchotodos.dto.RemoveTodoRequest;
import org.example.maraminchotodos.dto.UpdateTodoRequest;
import org.example.maraminchotodos.repository.ArchiveTodoRepository;
import org.example.maraminchotodos.repository.TodoDefaultRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
class TodoControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    TodoDefaultRepository repository;
    @Autowired
    ArchiveTodoRepository archiveTodoRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        repository.removeAll();
    }
    @AfterEach
    void cleanup() {
        repository.removeAll();
    }

    @Test
    @DisplayName("CreateTODO: TODO추가에 성공한다.")
    void createTodo() throws Exception {
        final String url = "/Todos";
        final Long userId = 1L;
        final String title = "MyTitle";
        final String content = "MyContent";
        final CreateTodoRequest request = new CreateTodoRequest(userId, title, content);

        final String requestBody = objectMapper.writeValueAsString(request);
        System.out.println(request);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().isCreated());

        List<Todo> todos = repository.getTodoByUserId(1L);
        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getUserId()).isEqualTo(userId);
        assertThat(todos.get(0).getContent()).isEqualTo(content);
        assertThat(todos.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("DelteTODO: TODO제거에 성공한다. ")
    void deleteTodo() throws Exception {
        final String url = "/Todos";
        final Long userId = 1L;
        final String title = "MyTitle";
        final String content = "MyContent";
        final CreateTodoRequest request = new CreateTodoRequest(userId, title, content);
        repository.addTodo(request);

        // Assign Test and expect
        List<Todo> todos = repository.getTodoByUserId(1L);
        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getTitle()).isEqualTo(title);
        final Long removeTodoId = todos.get(0).getId();

        // Remove Test
        final RemoveTodoRequest removeTodoRequest = new RemoveTodoRequest(removeTodoId);
        final String removeRequestBody = objectMapper.writeValueAsString(removeTodoRequest);
        ResultActions deleteResult = mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(removeRequestBody));

        deleteResult.andExpect(status().isOk());

        List<Todo> afterDeleteTodos = repository.getTodoByUserId(userId);
        assertThat(afterDeleteTodos).hasSize(1);

        // Archive Test
        var archivedTodos = archiveTodoRepository.getTodoByOriginalId(new GetTodoByIdRequest(removeTodoId));
        assertThat(archivedTodos).hasSize(1);
        assertThat(archivedTodos.get(0).getTitle()).isEqualTo(title);
        assertThat(archivedTodos.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("Patch TODO 업데이트에 성공한다. ")
    void updateTodo() throws Exception {
        final String url = "/Todos";
        final Long userId = 1L;
        final String title = "MyTitle";
        final String content = "MyContent";
        final String updateTitle = "Update Title";
        final String updateContent = "Update Content";
        final CreateTodoRequest request = new CreateTodoRequest(userId, title, content);
        repository.addTodo(request);
        final var todos = repository.getTodoByUserId(userId);
        assertThat(todos).hasSize(1);
        final var targetTodo = todos.get(0);
        assertThat(targetTodo.getTitle()).isEqualTo(title);
        assertThat(targetTodo.getContent()).isEqualTo(content);

        final UpdateTodoRequest updateTodoRequest = new UpdateTodoRequest(
                targetTodo.getId(),
                Optional.of(updateTitle),
                null
        );
        final String updateTodoFirstRequestBody = objectMapper.writeValueAsString(updateTodoRequest);

        ResultActions updateResult = mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateTodoFirstRequestBody));

        updateResult.andExpect(status().isOk());
        final var firstUpdateTodo = repository.getTodo(targetTodo.getId());
        if (firstUpdateTodo.isEmpty()) {
            throw new RuntimeException("No data to access that");
        }
        System.out.println(firstUpdateTodo.get().getContent() + " " + firstUpdateTodo.get().getTitle());
        assertThat(firstUpdateTodo.get().getTitle()).isEqualTo(updateTitle);
        assertThat(firstUpdateTodo.get().getContent()).isEqualTo(content);

        final UpdateTodoRequest updateTodoRequest2 = new UpdateTodoRequest(
                targetTodo.getId(),
                null,
                Optional.of(updateContent)
        );
        final String updateTodoRequestBody2 = objectMapper.writeValueAsString(updateTodoRequest2);

        ResultActions updateSecondResult = mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateTodoRequestBody2));

        updateSecondResult.andExpect(status().isOk());
        final var secondUpdateTodo = repository.getTodo(targetTodo.getId());
        if (secondUpdateTodo.isEmpty()) {
            throw new RuntimeException("The Data is null");
        }
        assertThat(secondUpdateTodo.get().getTitle()).isEqualTo(updateTitle);
        assertThat(secondUpdateTodo.get().getContent()).isEqualTo(updateContent);
    }
}