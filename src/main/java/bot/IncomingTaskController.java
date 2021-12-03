package bot;

import bot.domen.Task;
import org.springframework.web.bind.annotation.*;

@RestController
public class IncomingTaskController {
    @PostMapping(value = "task", consumes = "application/json")
    void receiveTask(@RequestBody Task task, @RequestHeader long userId) {
        System.out.println("GotTask: " + task.toString());
    }
}
