package bot.handlers;

import bot.BackendConnector;
import bot.domen.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
@Slf4j
public class TasksMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public TasksMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setText(TextMessage.tasks_list);

        if (update.hasMessage()) {
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String userId = update.getMessage().getFrom().getId() + "";
            try {
                setInlineTaskKeyboard(message, userId, getTasks(userId));
            } catch (Exception e) {
                message.setText(TextMessage.error);
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            }
        }
        if (update.hasCallbackQuery()) {
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            String query = update.getCallbackQuery().getData().replace("/getTaskByTag", "");
            long tagId = Long.parseLong(query);
            String userId = update.getCallbackQuery().getFrom().getId() + "";
            try {
                setInlineTaskKeyboard(message, userId, getTasks(userId, tagId));
            } catch (Exception e) {
                message.setText(TextMessage.error);
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            }
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.tasks);
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/getTaskByTag");
        }
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, String userId, List<Task> tasks) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Task task : tasks) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(task.getContent());
            button.setCallbackData(String.format("/taskget%d", task.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    private List<Task> getTasks(String userId) throws Exception {
        return backendConnector.getTasks(userId);
    }

    private List<Task> getTasks(String userId, long tagId) throws Exception {
        return backendConnector.getTasksByTag(userId, tagId);
    }
}
