package bot.controllers;

import bot.Bot;
import bot.entities.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class IncomingTaskController {
    private final Bot bot;

    @Autowired
    public IncomingTaskController(Bot bot) {
        this.bot = bot;
    }


    @PostMapping(value = "task", consumes = "application/json")
    void receiveTask(@RequestBody Task task, @RequestHeader long userId) {
        log.info("GotTask: " + task.toString());
        bot.sendTaskToUser(task, userId);
    }
}
