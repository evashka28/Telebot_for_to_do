package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Project;
import bot.domen.Task;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(value = 1)
public class GiveTaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public GiveTaskMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        String userId = update.getMessage().getFrom().getId() + "";
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        Task task = null;
        try {
            task = getTask(userId);
            String taskContent = task.getContent() + "";
            message.setText("Отличное время, чтобы изучить что-то новое. Например  это:" + taskContent);
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }

        InlineKeyboards.setInlineTaskKeyboard(message, userId, task.getId());

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase("Дай Задачу");
        }
        return false;
    }

    private Task getTask(String userId) throws Exception {
        return backendConnector.getOneTask(userId);
    }

}
