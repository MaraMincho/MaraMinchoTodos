package org.example.maraminchotodos.cnotroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.maraminchotodos.domain.Todo;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.repository.TodoDefaultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    TodoDefaultRepository repository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("CreateTODO: TODO추가에 성공한다.")
    @Test
    void createTodo() throws Exception {
        final String url = "/Todos";
        final Long userId = 1L;
        final String title = "MyTitle";
        final String content = "MyContent";
        final CreateTodoRequest request = new CreateTodoRequest(userId, title, content);

        final String requestBody = objectMapper.writeValueAsString(request);
        System.out.println(request);

        repository.removeAll();

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

}