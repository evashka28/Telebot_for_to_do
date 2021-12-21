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
public class GiveOneTaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public GiveOneTaskMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        String tagId = update.getCallbackQuery().getData().replace("/getOneTaskByTag", "");
        Task task;
        try {
            task = getTaskByTag(userId, tagId);
            String taskContent = task.getContent() + "";
            message.setText(TextMessage.goodTime + taskContent);
            InlineKeyboards.setInlineTaskKeyboard(message, userId, task.getId());
        } catch (Exception e) {
            message.setText(TextMessage.havenotTask);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/getOneTaskByTag");
        }
        return false;
    }

    private Task getTaskByTag(String userId, String tagId) throws Exception {
        return backendConnector.getTaskByTag(tagId, userId);
    }
}
