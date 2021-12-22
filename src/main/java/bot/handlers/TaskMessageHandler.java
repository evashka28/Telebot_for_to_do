package bot.handlers;

import bot.connectors.BackendConnector;
import bot.exceptions.BackendConnectorException;
import bot.keyboards.InlineKeyboards;
import bot.TextMessage;
import bot.entities.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
@Slf4j
public class TaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public TaskMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/taskget", "");

        Task task;
        try {
            task = backendConnector.getTask(userId, Long.parseLong(query));
            message.setText(task.getContent());
            InlineKeyboards.setInlineTaskKeyboard(message, userId, task.getId());
        } catch (BackendConnectorException e) {
            message.setText(TextMessage.error);
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/taskget");
        return false;
    }


}
