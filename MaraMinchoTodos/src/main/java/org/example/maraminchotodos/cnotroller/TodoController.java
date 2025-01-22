package org.example.maraminchotodos.cnotroller;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.dto.CreateTodoRequest;
import org.example.maraminchotodos.dto.CreateTodoResponse;
import org.example.maraminchotodos.dto.GetTodoResponse;
import org.example.maraminchotodos.service.TodoService;
import org.example.maraminchotodos.service.normal.NormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {
    private final NormalService normalService;

    @GetMapping("/Todos")
    public ResponseEntity<GetTodoResponse> getTodos() {
        return ResponseEntity.ok(normalService.getTodos());
    }


    @PostMapping("/Todos")
    //@RequestBody 는 post body 를 요청으로 가져온다.
    public ResponseEntity<CreateTodoResponse> createTodo(@RequestBody CreateTodoRequest request) {
        normalService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateTodoResponse("OK"));
    }
}
