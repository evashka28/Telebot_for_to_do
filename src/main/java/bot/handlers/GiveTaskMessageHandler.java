package bot.handlers;

import bot.connectors.BackendConnector;
import bot.keyboards.InlineKeyboards;
import bot.TextMessage;
import bot.entities.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
@Slf4j
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
            message.setText(TextMessage.goodTime + taskContent);
            InlineKeyboards.setInlineTaskKeyboard(message, userId, task.getId());
        } catch (Exception e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }



        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.giveTask);
        }
        return false;
    }

    private Task getTask(String userId) throws Exception {
        return backendConnector.getOneTask(userId);
    }

}
