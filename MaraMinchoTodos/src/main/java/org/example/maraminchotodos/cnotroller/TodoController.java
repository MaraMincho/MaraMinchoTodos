package org.example.maraminchotodos.cnotroller;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.dto.*;
import org.example.maraminchotodos.service.normal.NormalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //MARK: - PUT과 Patch의 다른점은 PUT은 전체를 바꾸는 것이고 PATCH는 일부를 바꾸는 것 입니다.
    @PatchMapping("/Todos")
    public ResponseEntity<UpdateTodoResponse> updateTodo(@RequestBody UpdateTodoRequest request) {
        normalService.updateTodo(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateTodoResponse("OK"));
    }
}
