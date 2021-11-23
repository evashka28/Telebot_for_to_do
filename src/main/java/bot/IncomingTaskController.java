package bot;

import org.springframework.web.bind.annotation.*;

@RestController
public class IncomingTaskController {
    @PostMapping(value = "task", consumes = "application/json")
    void receiveTask(@RequestBody Task task, @RequestHeader long userId) {}
}
