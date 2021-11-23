package bot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomingTaskController {
    @GetMapping(value = "task", consumes = "application/json")
    void receiveTask(@RequestBody Task task, @RequestHeader long userId) {}
}
