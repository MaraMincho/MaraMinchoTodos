package org.example.maraminchotodos.cnotroller;

import lombok.RequiredArgsConstructor;
import org.example.maraminchotodos.repository.MemoryExceedExample;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public ResponseEntity<String> getHealthCheck() {
        var message = "health check is good";
        return ResponseEntity
                .ok()
                .body(message);
    }
    @GetMapping("/Tests")
    public void test() {
        MemoryExceedExample.main(new String[]{});
    }
}
