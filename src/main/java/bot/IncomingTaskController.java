package bot;

import bot.domen.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IncomingTaskController {
    private final Bot bot;

    @Autowired
    public IncomingTaskController(Bot bot) {
        this.bot = bot;
    }


    @PostMapping(value = "task", consumes = "application/json")
    void receiveTask(@RequestBody Task task, @RequestHeader long userId) {
        System.out.println("GotTask: " + task.toString());
        bot.sendTaskToUser(task, userId);
    }
}
